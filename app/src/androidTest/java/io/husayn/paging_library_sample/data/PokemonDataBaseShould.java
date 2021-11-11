package io.husayn.paging_library_sample.data;

import static junit.framework.Assert.assertEquals;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** Created by husaynhakeem on 9/26/17. */
public class PokemonDataBaseShould {

  public static final String ANY_POKEMON_NAME = "pokemon";
  private Context context = InstrumentationRegistry.getTargetContext();
  private PokemonDao dao;

  @Before
  public void setUp() {
    dao = PokemonDataBase.getInstance(context).pokemonDao();
    dao.deleteAll();
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
    if (dao != null) dao.deleteAll();
  }

  private Pokemon[] anyPokemons() {
    Pokemon[] pokemons = new Pokemon[10];
    for (int i = 0; i < 10; i++) pokemons[i] = new Pokemon(i, ANY_POKEMON_NAME);
    return pokemons;
  }
}
