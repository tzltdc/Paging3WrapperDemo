package io.husayn.paging_library_sample.listing;

import android.app.Application;
import androidx.annotation.Nullable;
import androidx.paging.LoadType;
import androidx.paging.PagingSource;
import io.husayn.paging_library_sample.R;
import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.data.PokemonDao;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

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

  public PagingSource<Integer, Pokemon> pokemonLocalPagingSource(PagingQuery query) {
    String name = query.searchKey();
    return name == null ? pokemonDao.allByAsc() : pokemonDao.queryBy(name);
  }

  public void delete(@Nullable String key) {
    if (key == null) {
      pokemonDao.deleteAll();
    } else {
      pokemonDao.deleteByQuery(key);
    }
  }

  public void insertAll(List<Pokemon> list) {
    pokemonDao.insertAll(list);
  }

  /**
   * Insert new Pokemons into database, which invalidates the current PagingData, allowing Paging to
   * present the updates in the DB.
   */
  public void flushDbData(LoadType loadType, String searchKey, SearchPokemonResponse response) {
    if (loadType == LoadType.REFRESH) {
      Timber.w("tonny delete");
      delete(searchKey);
    }
    Timber.w("tonny insertAll :%s", response.list().size());
    pokemonDao.insertAll(response.list());
  }
}
