package io.husayn.paging_library_sample.listing;

import io.view.header.HeaderErrorCallback;
import javax.inject.Inject;

public class HeaderErrorAction extends io.view.header.HeaderErrorAction {

  private final PokemonAdapter pokemonAdapter;

  @Inject
  public HeaderErrorAction(PokemonAdapter pokemonAdapter) {
    this.pokemonAdapter = pokemonAdapter;
  }

  @Override
  public String text() {
    return "Reload";
  }

  @Override
  public HeaderErrorCallback callback() {
    return error -> pokemonAdapter.retry();
  }
}
