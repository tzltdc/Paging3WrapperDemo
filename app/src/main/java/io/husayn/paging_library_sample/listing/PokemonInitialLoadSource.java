package io.husayn.paging_library_sample.listing;

import androidx.paging.RemoteMediator.MediatorResult;
import androidx.paging.RemoteMediator.MediatorResult.Success;
import io.reactivex.Single;
import io.thread.WorkerScheduler;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import timber.log.Timber;

public class PokemonInitialLoadSource {

  private final PokemonRepo pokemonRepo;
  private final PokemonRemoteSource pokemonRemoteSource;
  private final WorkerScheduler scheduler;

  @Inject
  public PokemonInitialLoadSource(
      PokemonRemoteSource pokemonRemoteSource, PokemonRepo pokemonRepo, WorkerScheduler scheduler) {
    this.pokemonRemoteSource = pokemonRemoteSource;
    this.pokemonRepo = pokemonRepo;
    this.scheduler = scheduler;
  }

  /**
   * The network load method takes an optional after=<Pokemon.id> parameter. For every page after
   * the first, pass the last Pokemon ID to let it continue from where it left off. For REFRESH,
   * pass null to load the first page.
   */
  public Single<MediatorResult> load(PagingQueryParam query) {
    Timber.i("initial load with query :%s", query);
    return simulateError(query)
        ? simulateError()
        : execute(PagingRequestMapper.defaultPagingRequest(query));
  }

  private boolean simulateError(PagingQueryParam query) {
    return FilterOptionProvider.INITIAL_LOAD_ERROR.equals(query.searchKey());
  }

  private Single<MediatorResult> simulateError() {
    return Single.timer(1000, TimeUnit.MILLISECONDS, scheduler.get())
        .map(ignored -> new MediatorResult.Error(new RuntimeException("Simulated error")));
  }

  private Single<MediatorResult> execute(PagingRequest pagingRequest) {
    return pokemonRemoteSource
        .fetch(pagingRequest)
        .map(this::pagingAction)
        .map(this::success)
        .onErrorResumeNext(this::error);
  }

  private PagingAction pagingAction(PageActionResult data) {
    return PagingAction.create(PagingQueryAction.LoadType.REFRESH, data);
  }

  private Single<MediatorResult> error(Throwable e) {
    Timber.e(e, "initial load error");
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
