package io.husayn.paging_library_sample;

import androidx.annotation.NonNull;
import com.uber.rib.core.Worker;
import com.uber.rib.core.WorkerScopeProvider;
import javax.inject.Inject;

public class InitDatabaseWorker implements Worker {

  private final AppStatusRepo appStatusRepo;
  private final PokemonRepo pokemonRepo;

  @Inject
  public InitDatabaseWorker(AppStatusRepo appStatusRepo, PokemonRepo pokemonRepo) {
    this.appStatusRepo = appStatusRepo;
    this.pokemonRepo = pokemonRepo;
  }

  @Override
  public void onStart(@NonNull WorkerScopeProvider lifecycle) {
    if (!appStatusRepo.initialized()) {
      pokemonRepo.populateDB();
      appStatusRepo.markAsInitialized();
    }
  }
}
