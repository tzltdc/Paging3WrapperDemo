package paging.wrapper.db;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.paging.PagingSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Flowable;
import java.util.List;
import paging.wrapper.model.data.Pokemon;

@Dao
public interface PokemonDao {

  @Query("SELECT * FROM pokemon ORDER BY id ASC")
  PagingSource<Integer, Pokemon> allByAsc();

  @Query("SELECT * FROM pokemon  WHERE name like '%' || :name || '%' ORDER BY id ASC")
  PagingSource<Integer, Pokemon> queryBy(String name);

  @Nullable
  @Query("SELECT * FROM pokemon WHERE name like '%' || :name || '%'  ORDER BY id DESC limit 1")
  Pokemon lastItemOrNull(String name);

  @Query("SELECT * FROM pokemon WHERE id =:id")
  Flowable<Pokemon> queryById(int id);

  @Nullable
  @Query("SELECT * FROM pokemon ORDER BY id DESC limit 1")
  Pokemon lastItemOrNull();

  @VisibleForTesting()
  @Query("SELECT COUNT(*) FROM pokemon")
  Integer pokemonsCount();

  @Insert
  void insert(Pokemon... pokemons);

  @Insert
  void insertAll(List<Pokemon> pokemons);

  @Query("DELETE FROM pokemon")
  int deleteAll();

  @Query("DELETE FROM pokemon WHERE id = :id")
  void deleteById(long id);

  @Query("DELETE FROM pokemon WHERE name like '%' || :name || '%'")
  int deleteByQuery(String name);

  @Query("UPDATE pokemon set name = :name WHERE id = :id")
  int update(long id, String name);

  @Query("select * from pokemon WHERE id = :id")
  @Nullable
  Pokemon findById(int id);
}
