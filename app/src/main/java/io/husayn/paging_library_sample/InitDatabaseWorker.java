package io.husayn.paging_library_sample;

import com.uber.autodispose.ScopeProvider;
import io.app.config.AppConfig;
import javax.inject.Inject;

public class InitDatabaseWorker implements AutoDisposeWorker {

  private final AppConfig appConfig;
  private final AppStatusRepo appStatusRepo;
  private final PokemonRepo pokemonRepo;

  @Inject
  public InitDatabaseWorker(
      AppConfig appConfig, AppStatusRepo appStatusRepo, PokemonRepo pokemonRepo) {
    this.appConfig = appConfig;
    this.appStatusRepo = appStatusRepo;
    this.pokemonRepo = pokemonRepo;
  }

  @Override
  public void attach(ScopeProvider scopeProvider) {
    if (appConfig.initializeDatabase()) {
      initIfNeeded();
    }
  }

  private void initIfNeeded() {
    if (!appStatusRepo.initialized()) {
      pokemonRepo.populateDB();
      appStatusRepo.markAsInitialized();
    }
  }
}
