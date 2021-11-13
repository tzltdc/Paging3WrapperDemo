package io.husayn.paging_library_sample;

import com.uber.autodispose.ScopeProvider;
import io.app.config.AppConfig;
import io.husayn.paging_library_sample.listing.PokemonRepo;
import io.thread.WorkerScheduler;
import javax.inject.Inject;

public class InitDatabaseWorker implements AutoDisposeWorker {

  private final AppConfig appConfig;
  private final AppStatusRepo appStatusRepo;
  private final PokemonRepo pokemonRepo;
  private final WorkerScheduler workerScheduler;

  @Inject
  public InitDatabaseWorker(
      AppConfig appConfig,
      AppStatusRepo appStatusRepo,
      PokemonRepo pokemonRepo,
      WorkerScheduler workerScheduler) {
    this.appConfig = appConfig;
    this.appStatusRepo = appStatusRepo;
    this.pokemonRepo = pokemonRepo;
    this.workerScheduler = workerScheduler;
  }

  @Override
  public void attach(ScopeProvider scopeProvider) {
    if (appConfig.initializeDatabase()) {
      workerScheduler.get().scheduleDirect(this::initIfNeeded);
    }
  }

  private void initIfNeeded() {
    if (!appStatusRepo.initialized()) {
      pokemonRepo.populateDB();
      appStatusRepo.markAsInitialized();
    }
  }
}
