package io.husayn.paging_library_sample.listing;

import io.husayn.paging_library_sample.data.Pokemon;
import java.util.List;

class SearchPokemonResponse {

  private final List<Pokemon> list;

  public SearchPokemonResponse(List<Pokemon> list) {
    this.list = list;
  }

  public List<Pokemon> getPokemons() {
    return list;
  }
}
