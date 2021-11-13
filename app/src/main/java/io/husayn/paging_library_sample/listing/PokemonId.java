package io.husayn.paging_library_sample.listing;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class PokemonId {

  public abstract int get();

  public static PokemonId create(int get) {
    return new AutoValue_PokemonId(get);
  }
}
