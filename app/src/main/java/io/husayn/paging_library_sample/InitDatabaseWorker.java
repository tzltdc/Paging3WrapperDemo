package io.husayn.paging_library_sample;

import com.uber.autodispose.ScopeProvider;
import javax.inject.Inject;

public class InitDatabaseWorker implements AutoDisposeWorker {

  private final AppStatusRepo appStatusRepo;
  private final PokemonRepo pokemonRepo;

  @Inject
  public InitDatabaseWorker(AppStatusRepo appStatusRepo, PokemonRepo pokemonRepo) {
    this.appStatusRepo = appStatusRepo;
    this.pokemonRepo = pokemonRepo;
  }

  @Override
  public void attach(ScopeProvider scopeProvider) {
    if (!appStatusRepo.initialized()) {
      pokemonRepo.populateDB();
      appStatusRepo.markAsInitialized();
    }
  }
}
