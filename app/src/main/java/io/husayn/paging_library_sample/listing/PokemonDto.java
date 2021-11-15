package io.husayn.paging_library_sample.listing;

import com.google.auto.value.AutoValue;
import java.util.List;
import paging.wrapper.model.data.Pokemon;

@AutoValue
public abstract class PokemonDto {

  public abstract List<Pokemon> list();

  public static PokemonDto create(List<Pokemon> list) {
    return new AutoValue_PokemonDto(list);
  }
}
