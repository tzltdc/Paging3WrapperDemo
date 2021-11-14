package io.husayn.paging_library_sample.listing;

import androidx.paging.RemoteMediator.MediatorResult;
import androidx.paging.RemoteMediator.MediatorResult.Success;
import io.husayn.paging_library_sample.ActivityScope;
import io.husayn.paging_library_sample.data.Pokemon;
import io.reactivex.Single;
import io.thread.WorkerScheduler;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import timber.log.Timber;

@ActivityScope
public class PokemonLoadMoreSource {

  private final PokemonRepo pokemonRepo;
  private final PokemonMediatorResultRepo pokemonMediatorResultRepo;
  private final WorkerScheduler workerScheduler;
  private int retryCount = 0;

  @Inject
  public PokemonLoadMoreSource(
      PokemonRepo pokemonRepo,
      PokemonMediatorResultRepo pokemonMediatorResultRepo,
      WorkerScheduler workerScheduler) {
    this.pokemonRepo = pokemonRepo;
    this.pokemonMediatorResultRepo = pokemonMediatorResultRepo;
    this.workerScheduler = workerScheduler;
    Timber.i("created:%s", this);
  }

  /**
   * You must explicitly check if the last item is null when appending, since passing null to
   * networkService is only valid for initial load. If lastItem is null it means no items were
   * loaded after the initial ø REFRESH and there are no more items to load.
   */
  public Single<MediatorResult> loadingMore(PagingQueryContext context) {
    return showLoadMoreError(context.description())
        ? simulateError()
        : Single.defer(() -> execute(context.param()));
  }

  private boolean showLoadMoreError(String desc) {
    return FilterOptionProvider.LOAD_MORE_ERROR.equals(desc) && retryCount++ % 2 == 0;
  }

  private Single<MediatorResult> simulateError() {
    return Single.just(error()).delay(800, TimeUnit.MILLISECONDS, workerScheduler.get());
  }

  private MediatorResult error() {
    return new MediatorResult.Error(new RuntimeException("loading more happened"));
  }

  private Single<MediatorResult> execute(PagingQueryParam query) {
    Pokemon lastItem = pokemonRepo.lastItemOrNull(query);
    Timber.i("loading more with query:%s, last_item:%s", query, lastItem);
    if (lastItem == null) {
      return Single.just(new Success(true));
    } else {
      PagingRequest pagingRequest = PagingRequestMapper.nextPagingRequest(query, lastItem);
      return pokemonMediatorResultRepo.request(pagingRequest);
    }
  }
}
