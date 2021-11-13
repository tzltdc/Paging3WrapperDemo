package io.husayn.paging_library_sample.data;

import static junit.framework.Assert.assertEquals;

import android.app.Application;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import io.app.config.AppConfig;
import io.app.config.AppContext;
import javax.inject.Inject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/** Created by husaynhakeem on 9/26/17. */
@RunWith(AndroidJUnit4.class)
public class PokemonDataBaseShould {

  public static final String ANY_POKEMON_NAME = "pokemon";
  @Inject PokemonDao dao;

  @Before
  public void setUp() {
    DaggerTestAppComponent.factory()
        .create(AppContext.create(AppConfig.DEFAULT_CONFIG, testApplication()))
        .inject(this);
    dao.deleteAll();
  }

  public static Application testApplication() {
    return (Application)
        InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
  }

  @Test
  public void returnZero_whenDatabaseIsEmpty() {
    int dbSize = dao.pokemonsCount();
    assertEquals(0, dbSize);
  }

  @Test
  public void returnCorrectSize_whenDatabaseIsFull() {
    dao.insert(anyPokemons());
    int dbSize = dao.pokemonsCount();
    assertEquals(10, dbSize);
  }

  @After
  public void tearDown() {
    if (dao != null) {
      dao.deleteAll();
    }
  }

  private Pokemon[] anyPokemons() {
    Pokemon[] pokemons = new Pokemon[10];
    for (int i = 0; i < 10; i++) {
      pokemons[i] = new Pokemon(i, ANY_POKEMON_NAME);
    }
    return pokemons;
  }
}
