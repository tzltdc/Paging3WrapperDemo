package paging.wrapper.di.app;

import android.app.Application;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import paging.wrapper.db.PokemonDao;
import paging.wrapper.db.PokemonDataBase;

@Module
public abstract class DatabaseModule {

  private static final String POKEMON_DB = "pokemon.db";

  @AppScope
  @Provides
  public static PokemonDataBase pokemonDataBase(Application application) {
    return Room.databaseBuilder(application, PokemonDataBase.class, POKEMON_DB).build();
  }

  @AppScope
  @Provides
  public static PokemonDao pokemonDao(PokemonDataBase pokemonDataBase) {
    return pokemonDataBase.pokemonDao();
  }
}
