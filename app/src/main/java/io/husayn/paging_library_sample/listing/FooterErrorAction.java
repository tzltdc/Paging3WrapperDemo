package io.husayn.paging_library_sample.listing;

import io.view.header.FooterEntity.Error;
import javax.inject.Inject;
import timber.log.Timber;

public class FooterErrorAction extends Error.ErrorAction {

  private final PokemonAdapter pokemonAdapter;

  @Inject
  public FooterErrorAction(PokemonAdapter pokemonAdapter) {
    Timber.i("FooterErrorAction PokemonAdapter:%s", pokemonAdapter);
    this.pokemonAdapter = pokemonAdapter;
  }

  @Override
  public String text() {
    return "Retry";
  }

  @Override
  public Callback callback() {
    return error -> executeRetry();
  }

  private void executeRetry() {
    Timber.i("Retry to fetch the data");
    pokemonAdapter.retry();
  }
}
