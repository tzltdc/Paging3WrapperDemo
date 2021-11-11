package io.husayn.paging_library_sample.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
    entities = {Pokemon.class, Repo.class, User.class, UserRepoJoin.class},
    version = 1)
public abstract class PokemonDataBase extends RoomDatabase {

  private static final String POKEMON_DB = "pokemon.db";
  private static PokemonDataBase pokemonDB;

  public static PokemonDataBase getInstance(Context context) {
    if (pokemonDB == null) {
      pokemonDB =
          Room.databaseBuilder(context.getApplicationContext(), PokemonDataBase.class, POKEMON_DB)
              .build();
    }
    return pokemonDB;
  }

  public abstract PokemonDao pokemonDao();

  public abstract RepoDao getRepoDao();

  public abstract UserDao getUserDao();

  public abstract UserRepoJoinDao getUserRepoJoinDao();
}
