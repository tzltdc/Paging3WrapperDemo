package io.husayn.paging_library_sample.data;

import androidx.annotation.VisibleForTesting;
import androidx.paging.PagingSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface PokemonDao {

  @Query("SELECT * FROM pokemon  ORDER BY id DESC")
  PagingSource<Integer, Pokemon> allByDesc();

  @Query("SELECT * FROM pokemon ORDER BY id ASC")
  PagingSource<Integer, Pokemon> allByAsc();

  @VisibleForTesting()
  @Query("SELECT COUNT(*) FROM pokemon")
  Integer pokemonsCount();

  @Insert
  void insert(Pokemon... pokemons);

  @Insert
  void insertAll(List<Pokemon> pokemons);

  @VisibleForTesting()
  @Query("DELETE FROM pokemon")
  void deleteAll();

  @Query("DELETE FROM pokemon WHERE id = :id")
  void deleteById(long id);

  @Query("DELETE FROM pokemon WHERE name like '%' || :name || '%'")
  void deleteByQuery(String name);

  @Query("UPDATE pokemon set name = :name WHERE id = :id")
  int update(long id, String name);
}
