package io.husayn.paging_library_sample.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import paging.wrapper.model.data.Pokemon;

@Database(
    entities = {Pokemon.class, Repo.class, User.class, UserRepoJoin.class},
    version = 1)
public abstract class PokemonDataBase extends RoomDatabase {

  public abstract PokemonDao pokemonDao();

  public abstract RepoDao getRepoDao();

  public abstract UserDao getUserDao();

  public abstract UserRepoJoinDao getUserRepoJoinDao();
}
