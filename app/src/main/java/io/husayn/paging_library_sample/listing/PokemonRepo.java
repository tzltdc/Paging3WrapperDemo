package io.husayn.paging_library_sample.listing;

import android.app.Application;
import androidx.annotation.Nullable;
import androidx.paging.PagingSource;
import io.husayn.paging_library_sample.R;
import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.data.PokemonDao;
import io.husayn.paging_library_sample.data.PokemonDataBase;
import io.reactivex.Flowable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

public class PokemonRepo {

  private final PokemonDataBase dataBase;
  private final Application application;
  private final PokemonDao pokemonDao;

  @Inject
  public PokemonRepo(PokemonDataBase dataBase, Application application, PokemonDao pokemonDao) {
    this.dataBase = dataBase;
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

  public PagingSource<Integer, Pokemon> pokemonLocalPagingSource(PagingQueryParam query) {
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

  /**
   * Insert new Pokemons into database, which invalidates the current PagingData, allowing Paging to
   * present the updates in the DB.
   */
  public void flushDbData(PageActionResult data) {
    Timber.i("flushDbData");
    dataBase.runInTransaction(() -> execute(data));
  }

  private void execute(PageActionResult data) {
    Timber.i("execute flushDbData");
    if (initialLoad(data)) {
      Timber.w("clear flushDbData first");
      delete(data.request().pagingQueryParam().searchKey());
    }
    List<Pokemon> list = data.response().list();
    Timber.w("insert new data :%s", list.size());
    pokemonDao.insertAll(list);
  }

  private boolean initialLoad(PageActionResult data) {
    return data.request().offSet() == 0;
  }

  /** Inspired from https://stackoverflow.com/a/66814196/4068957 */
  @Nullable
  public Pokemon lastItemOrNull(PagingQueryParam query) {
    String name = query.searchKey();
    return name == null
        ? pokemonDao.lastItemOrNull()
        : pokemonDao.lastItemOrNull(query.searchKey());
  }

  public Flowable<Pokemon> observe(PokemonId id) {
    return pokemonDao.queryById(id.get());
  }
}
