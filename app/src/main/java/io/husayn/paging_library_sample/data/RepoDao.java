package io.husayn.paging_library_sample.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface RepoDao {

  @Insert
  void insert(Repo repo);

  @Update
  void update(Repo... repos);

  @Delete
  void delete(Repo... repos);

  @Query("SELECT * FROM repo")
  List<Repo> getAllRepos();

  //    @Query("SELECT * FROM repo WHERE userId=:userId")
  //    List<Repo> findRepositoriesForUser(final int userId);
}
