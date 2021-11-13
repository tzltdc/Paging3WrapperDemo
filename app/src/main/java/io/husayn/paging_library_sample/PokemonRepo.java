package io.husayn.paging_library_sample;

import android.app.Application;
import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.data.PokemonDao;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class PokemonRepo {

  private final Application application;
  private final PokemonDao pokemonDao;

  @Inject
  public PokemonRepo(Application application, PokemonDao pokemonDao) {
    this.application = application;
    this.pokemonDao = pokemonDao;
  }

  public void populateDB() {
    pokemonDao.insert(pokemonList());
  }

  private Pokemon[] pokemonList() {
    List<Pokemon> list = new ArrayList<>();
    String[] pokemonNames = application.getResources().getStringArray(R.array.pokemon_names);
    int index = 0;
    for (int j = 0; j < 1; j++) {
      for (String pokemonName : pokemonNames) {
        list.add(new Pokemon(++index, pokemonName));
      }
    }
    return list.toArray(new Pokemon[0]);
  }
}
