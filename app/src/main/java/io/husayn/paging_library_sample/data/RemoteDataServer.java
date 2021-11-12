package io.husayn.paging_library_sample.data;

import io.husayn.paging_library_sample.PokemonApplication;
import io.husayn.paging_library_sample.R;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RemoteDataServer {

  public static List<Pokemon> all() {
    return Collections.unmodifiableList(insertInternal());
  }

  public static Pokemon indexBy(String name) {
    return Observable.fromIterable(all()).filter(item -> item.name.equals(name)).blockingFirst();
  }

  private static List<Pokemon> insertInternal() {
    List<Pokemon> list = new ArrayList<>();
    String[] name =
        PokemonApplication.getContext().getResources().getStringArray(R.array.pokemon_names);
    int index = 0;
    for (String pokemonName : name) {
      list.add(new Pokemon(++index, pokemonName));
    }
    return list;
  }
}
