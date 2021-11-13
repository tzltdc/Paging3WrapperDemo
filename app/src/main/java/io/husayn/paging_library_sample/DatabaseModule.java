package io.husayn.paging_library_sample;

import android.app.Application;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import io.husayn.paging_library_sample.data.PokemonDao;
import io.husayn.paging_library_sample.data.PokemonDataBase;

@Module
public abstract class DatabaseModule {

  private static final String POKEMON_DB = "pokemon.db";

  @AppScope
  @Provides
  public static PokemonDataBase pokemonDataBase(Application application) {
    return Room.databaseBuilder(application, PokemonDataBase.class, POKEMON_DB)
        .allowMainThreadQueries()
        .build();
  }

  @AppScope
  @Provides
  public static PokemonDao pokemonDao(PokemonDataBase pokemonDataBase) {
    return pokemonDataBase.pokemonDao();
  }
}
