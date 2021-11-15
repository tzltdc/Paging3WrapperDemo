package paging.wrapper.model.data;

import com.google.auto.value.AutoValue;
import java.util.List;

@AutoValue
public abstract class PokemonDto {

  public abstract List<Pokemon> list();

  public static PokemonDto create(List<Pokemon> list) {
    return new AutoValue_PokemonDto(list);
  }
}
