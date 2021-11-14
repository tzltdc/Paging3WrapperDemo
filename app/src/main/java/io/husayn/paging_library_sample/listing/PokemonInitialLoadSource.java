package io.husayn.paging_library_sample.listing;

import androidx.paging.RemoteMediator.MediatorResult;
import io.reactivex.Single;
import io.thread.WorkerScheduler;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import timber.log.Timber;

public class PokemonInitialLoadSource {

  private final PokemonMediatorResultRepo pokemonMediatorResultRepo;
  private final WorkerScheduler scheduler;

  @Inject
  public PokemonInitialLoadSource(
      PokemonMediatorResultRepo pokemonMediatorResultRepo, WorkerScheduler scheduler) {
    this.pokemonMediatorResultRepo = pokemonMediatorResultRepo;
    this.scheduler = scheduler;
  }

  /**
   * The network load method takes an optional after=<Pokemon.id> parameter. For every page after
   * the first, pass the last Pokemon ID to let it continue from where it left off. For REFRESH,
   * pass null to load the first page.
   */
  public Single<MediatorResult> load(PagingQueryParam query) {
    Timber.i("initial load with query :%s", query);
    return simulateError(query) ? simulateError() : execute(query);
  }

  private Single<MediatorResult> execute(PagingQueryParam query) {
    return pokemonMediatorResultRepo.request(PagingRequestMapper.defaultPagingRequest(query));
  }

  private boolean simulateError(PagingQueryParam query) {
    return FilterOptionProvider.INITIAL_LOAD_ERROR.equals(query.searchKey());
  }

  private Single<MediatorResult> simulateError() {
    return Single.timer(1000, TimeUnit.MILLISECONDS, scheduler.get())
        .map(ignored -> new MediatorResult.Error(new RuntimeException("Simulated initial error")));
  }
}
