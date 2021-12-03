package paging.wrapper.app;

import com.uber.autodispose.ScopeProvider;
import javax.inject.Inject;
import paging.wrapper.app.config.AppConfig;
import paging.wrapper.data.PokemonRepo;
import paging.wrapper.di.thread.AppScheduler;

public class InitDatabaseWorker implements AutoDisposeWorker {

  private final AppConfig appConfig;
  private final AppStatusRepo appStatusRepo;
  private final PokemonRepo pokemonRepo;
  private final AppScheduler appScheduler;

  @Inject
  public InitDatabaseWorker(
      AppConfig appConfig,
      AppStatusRepo appStatusRepo,
      PokemonRepo pokemonRepo,
      AppScheduler appScheduler) {
    this.appConfig = appConfig;
    this.appStatusRepo = appStatusRepo;
    this.pokemonRepo = pokemonRepo;
    this.appScheduler = appScheduler;
  }

  @Override
  public void attach(ScopeProvider scopeProvider) {
    if (appConfig.initializeDatabase()) {
      appScheduler.worker().scheduleDirect(this::initIfNeeded);
    }
  }

  private void initIfNeeded() {
    if (!appStatusRepo.initialized()) {
      pokemonRepo.populateDB();
      appStatusRepo.markAsInitialized();
    }
  }
}
