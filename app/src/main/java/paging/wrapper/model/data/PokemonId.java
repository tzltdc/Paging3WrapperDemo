package paging.wrapper.model.data;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class PokemonId {

  public abstract int get();

  public static PokemonId create(int get) {
    return new AutoValue_PokemonId(get);
  }
}
