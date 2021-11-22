package paging.wrapper.db;

import io.husayn.paging_library_sample.R;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import paging.wrapper.app.PokemonApplication;
import paging.wrapper.model.data.Pokemon;

public class RemoteDataServer {

  private static final int MAX = 151;

  public static List<Pokemon> all() {
    return Collections.unmodifiableList(trim(insertInternal()));
  }

  private static List<Pokemon> trim(List<Pokemon> rawList) {
    return rawList.subList(0, MAX);
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