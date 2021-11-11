package io.husayn.paging_library_sample.data;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.support.annotation.VisibleForTesting;

@Dao
public interface PokemonDao {

  @Query("SELECT * FROM pokemon  ORDER BY id DESC")
  DataSource.Factory<Integer, Pokemon> allByDesc();

  @Query("SELECT * FROM pokemon ORDER BY id ASC")
  DataSource.Factory<Integer, Pokemon> allByAsc();

  @VisibleForTesting()
  @Query("SELECT COUNT(*) FROM pokemon")
  Integer pokemonsCount();

  @Insert
  void insert(Pokemon... pokemons);

  @VisibleForTesting()
  @Query("DELETE FROM pokemon")
  void deleteAll();

  @Query("DELETE FROM pokemon WHERE id = :id")
  void deleteById(long id);

  @Query("UPDATE pokemon set name = :name WHERE id = :id")
  int update(long id, String name);
}
