package io.husayn.paging_library_sample.listing;

import io.view.header.HeaderEntity.Error.ErrorAction;
import javax.inject.Inject;

public class HeaderErrorAction extends ErrorAction {

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
  public Callback callback() {
    return error -> pokemonAdapter.retry();
  }
}
