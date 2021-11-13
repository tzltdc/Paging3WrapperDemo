package io.husayn.paging_library_sample.listing;

import androidx.annotation.NonNull;
import androidx.paging.LoadType;
import androidx.paging.PagingState;
import androidx.paging.RemoteMediator.MediatorResult.Success;
import androidx.paging.rxjava2.RxRemoteMediator;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.husayn.paging_library_sample.data.Pokemon;
import io.reactivex.Single;
import java.io.IOException;
import timber.log.Timber;

class ExampleRemoteMediator extends RxRemoteMediator<Integer, Pokemon> {

  private final PagingQuery query;
  private final PokemonRepo pokemonRepo;

  @AssistedInject
  ExampleRemoteMediator(@Assisted PagingQuery query, PokemonRepo pokemonRepo) {
    this.query = query;
    this.pokemonRepo = pokemonRepo;
  }

  @NonNull
  @Override
  public Single<InitializeAction> initializeSingle() {
    return InitializeActionMapper.initializeSingle();
  }

  @NonNull
  @Override
  public Single<MediatorResult> loadSingle(
      @NonNull LoadType loadType, @NonNull PagingState<Integer, Pokemon> state) {
    switch (loadType) {
      case REFRESH:
        return refresh(loadType);
      case APPEND:
        return append(state);
      case PREPEND:
      default:
        return ignorePrepend();
    }
  }

  /**
   * In this example, you never need to prepend, since REFRESH will always load the first page in
   * the list. Immediately return, reporting end of pagination.
   */
  private Single<MediatorResult> ignorePrepend() {
    Timber.w("tonny Prepend LoadType ignored");
    return Single.just(new MediatorResult.Success(true));
  }

  /**
   * The network load method takes an optional after=<Pokemon.id> parameter. For every page after
   * the first, pass the last Pokemon ID to let it continue from where it left off. For REFRESH,
   * pass null to load the first page.
   */
  private Single<MediatorResult> refresh(LoadType loadType) {
    Timber.w("tonny refresh :%s", loadType);
    PagingRequest pagingRequest = defaultPagingRequest(query);
    return execute(pagingRequest, loadType);
  }

  private PagingRequest defaultPagingRequest(PagingQuery query) {
    return PagingRequest.create(0, query, PagingQueryConfig.DEFAULT_QUERY_CONFIG);
  }

  /**
   * You must explicitly check if the last item is null when appending, since passing null to
   * networkService is only valid for initial load. If lastItem is null it means no items were
   * loaded after the initial ø REFRESH and there are no more items to load.
   */
  private Single<MediatorResult> append(PagingState<Integer, Pokemon> state) {
    Pokemon lastItem = pokemonRepo.lastItemOrNull(query);
    Timber.w("tonny append with query:%s, last_item:%s", query, lastItem);
    if (lastItem == null) {
      return Single.just(new MediatorResult.Success(true));
    } else {
      return execute(nextPagingRequest(query, lastItem), LoadType.APPEND);
    }
  }

  private Single<MediatorResult> execute(PagingRequest pagingRequest, LoadType loadType) {
    return ExampleBackendService.query(pagingRequest)
        .map(response -> PagingAction.create(response, pagingRequest, loadType))
        .map(this::success)
        .onErrorResumeNext(this::error);
  }

  private PagingRequest nextPagingRequest(PagingQuery query, Pokemon lastItem) {
    return PagingRequest.create(
        OffsetHelper.offset(lastItem, query), query, PagingQueryConfig.DEFAULT_QUERY_CONFIG);
  }

  private Single<MediatorResult> error(Throwable e) {
    if (e instanceof IOException) {
      return Single.just(new MediatorResult.Error(e));
    } else {
      return Single.error(e);
    }
  }

  private MediatorResult success(PagingAction action) {
    pokemonRepo.flushDbData(action);
    return new Success(endOfPaging(action));
  }

  private boolean endOfPaging(PagingAction action) {
    return EndOfPagingMapper.endOfPaging(action);
  }
}
