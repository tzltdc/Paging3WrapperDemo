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
  private int retryCount = 0;

  @Inject
  public PokemonInitialLoadSource(
      PokemonMediatorResultRepo pokemonMediatorResultRepo, WorkerScheduler scheduler) {
    this.pokemonMediatorResultRepo = pokemonMediatorResultRepo;
    this.scheduler = scheduler;
    Timber.i("created:%s", this);
  }

  /**
   * The network load method takes an optional after=<Pokemon.id> parameter. For every page after
   * the first, pass the last Pokemon ID to let it continue from where it left off. For REFRESH,
   * pass null to load the first page.
   */
  public Single<MediatorResult> load(PagingQueryContext query) {
    Timber.i("initial load with query :%s", query);
    return simulateError(query.description()) ? simulateError() : execute(query.param());
  }

  private Single<MediatorResult> execute(PagingQueryParam query) {
    return pokemonMediatorResultRepo.request(PagingRequestMapper.defaultPagingRequest(query));
  }

  private boolean simulateError(String desc) {
    return FilterOptionProvider.INITIAL_LOAD_ERROR.equals(desc) && retryCount++ % 2 == 0;
  }

  private Single<MediatorResult> simulateError() {
    return Single.timer(1000, TimeUnit.MILLISECONDS, scheduler.get())
        .map(ignored -> new MediatorResult.Error(new RuntimeException("Simulated initial error")));
  }
}
