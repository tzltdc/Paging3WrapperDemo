package io.husayn.paging_library_sample.listing;

import com.google.auto.value.AutoValue;
import io.husayn.paging_library_sample.data.Pokemon;
import java.util.List;

@AutoValue
abstract class PokemonDto {

  public abstract List<Pokemon> list();

  public static PokemonDto create(List<Pokemon> list) {
    return new AutoValue_PokemonDto(list);
  }
}
