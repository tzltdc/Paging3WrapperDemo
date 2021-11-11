package io.husayn.paging_library_sample.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface UserDao {

  @Insert
  void insert(User... user);

  @Update
  void update(User... user);

  @Delete
  void delete(User... user);
}
