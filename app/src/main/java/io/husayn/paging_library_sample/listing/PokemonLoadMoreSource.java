package io.husayn.paging_library_sample.listing;

import androidx.paging.RemoteMediator.MediatorResult;
import androidx.paging.RemoteMediator.MediatorResult.Success;
import io.husayn.paging_library_sample.data.Pokemon;
import io.reactivex.Single;
import io.thread.WorkerScheduler;
import javax.inject.Inject;
import timber.log.Timber;

public class PokemonLoadMoreSource {

  private final WorkerScheduler workerScheduler;
  private final PokemonRepo pokemonRepo;
  private final PokemonRemoteSource pokemonRemoteSource;

  @Inject
  public PokemonLoadMoreSource(
      WorkerScheduler workerScheduler,
      PokemonRepo pokemonRepo,
      PokemonRemoteSource pokemonRemoteSource) {
    this.workerScheduler = workerScheduler;
    this.pokemonRepo = pokemonRepo;
    this.pokemonRemoteSource = pokemonRemoteSource;
  }

  /**
   * You must explicitly check if the last item is null when appending, since passing null to
   * networkService is only valid for initial load. If lastItem is null it means no items were
   * loaded after the initial ø REFRESH and there are no more items to load.
   */
  public Single<MediatorResult> loadingMore(PagingQueryParam query) {
    return Single.just(query).flatMap(this::deferAppend).subscribeOn(workerScheduler.get());
  }

  private Single<MediatorResult> deferAppend(PagingQueryParam query) {
    return showLoadMoreError(query) ? simulateError() : execute(query);
  }

  private boolean showLoadMoreError(PagingQueryParam query) {
    return FilterOptionProvider.LOAD_MORE_ERROR.equals(query.searchKey());
  }

  private Single<MediatorResult> simulateError() {
    return Single.just(new MediatorResult.Error(new RuntimeException("loading more happened")));
  }

  private Single<MediatorResult> execute(PagingQueryParam query) {
    Pokemon lastItem = pokemonRepo.lastItemOrNull(query);
    Timber.i("tonny append with query:%s, last_item:%s", query, lastItem);
    if (lastItem == null) {
      return Single.just(new Success(true));
    } else {
      return execute(PagingRequestMapper.nextPagingRequest(query, lastItem));
    }
  }

  private Single<MediatorResult> execute(PagingRequest pagingRequest) {
    return pokemonRemoteSource
        .fetch(pagingRequest)
        .map(this::pagingAction)
        .map(this::success)
        .onErrorResumeNext(this::error);
  }

  private PagingAction pagingAction(PageActionResult data) {
    return PagingAction.create(PagingQueryAction.LoadType.APPEND, data);
  }

  private Single<MediatorResult> error(Throwable e) {
    Timber.e(e, "tonny error");
    return Single.just(new MediatorResult.Error(e));
  }

  private MediatorResult success(PagingAction action) {
    pokemonRepo.flushDbData(action);
    return new Success(endOfPaging(action));
  }

  private boolean endOfPaging(PagingAction action) {
    return EndOfPagingMapper.endOfPaging(action.data());
  }
}
