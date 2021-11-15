package paging.wrapper.data;

import androidx.paging.RemoteMediator.MediatorResult;
import androidx.paging.RemoteMediator.MediatorResult.Success;
import io.reactivex.Single;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import paging.wrapper.demo.ui.query.FilterOptionProvider;
import paging.wrapper.di.app.ActivityScope;
import paging.wrapper.di.thread.WorkerScheduler;
import paging.wrapper.mapper.PagingRequestMapper;
import paging.wrapper.model.data.PagingQueryContext;
import paging.wrapper.model.data.PagingQueryParam;
import paging.wrapper.model.data.PagingRequest;
import paging.wrapper.model.data.Pokemon;
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
        ? simulateError(context.param())
        : Single.defer(() -> execute(context.param()));
  }

  private boolean showLoadMoreError(String desc) {
    return FilterOptionProvider.LOAD_MORE_ERROR.equals(desc) && retryCount++ % 2 == 0;
  }

  private Single<MediatorResult> simulateError(PagingQueryParam param) {
    Timber.i(
        "[ttt]: PokemonLoadMoreSource will return a simulated loading more error for query :%s",
        param);
    return Single.just(error()).delay(800, TimeUnit.MILLISECONDS, workerScheduler.get());
  }

  private MediatorResult error() {
    return new MediatorResult.Error(new RuntimeException("loading more happened"));
  }

  private Single<MediatorResult> execute(PagingQueryParam query) {
    Pokemon lastItem = pokemonRepo.lastItemOrNull(query);
    Timber.i(
        "[ttt]: PokemonLoadMoreSource is to executing with query:%s, last_item:%s",
        query, lastItem);
    if (lastItem == null) {
      return skipLoadingMore(query);
    } else {
      PagingRequest pagingRequest = PagingRequestMapper.nextPagingRequest(query, lastItem);
      Timber.i(
          "[ttt]:loading more with assembled paging request:%s based on last_item:%s",
          pagingRequest, lastItem);
      return pokemonMediatorResultRepo.request(pagingRequest).doOnSuccess(this::logResult);
    }
  }

  /**
   * FIXME: 11/14/21 Just because last item is not available does not mean that it is 100% correct
   * that we should return the Success result as true.
   */
  private Single<MediatorResult> skipLoadingMore(PagingQueryParam query) {
    Timber.w(
        "[ttt]: PokemonLoadMoreSource has been concluded as no existing item for query :%s", query);
    return Single.just(new Success(true));
  }

  private void logResult(MediatorResult mediatorResult) {
    Timber.i(
        "[ttt]:PokemonLoadMoreSource concluded loading more action with mediatorResult success:%s",
        mediatorResult instanceof Success);
  }
}
