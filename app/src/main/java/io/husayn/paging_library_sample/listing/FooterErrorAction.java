package io.husayn.paging_library_sample.listing;

import io.view.header.FooterErrorCallback;
import javax.inject.Inject;
import timber.log.Timber;

public class FooterErrorAction extends io.view.header.FooterErrorAction {

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
  public FooterErrorCallback callback() {
    return error -> executeRetry();
  }

  private void executeRetry() {
    Timber.i("Retry to fetch the data");
    pokemonAdapter.retry();
  }
}
