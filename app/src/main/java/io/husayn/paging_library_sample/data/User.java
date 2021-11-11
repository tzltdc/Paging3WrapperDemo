package io.husayn.paging_library_sample.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = User.TABLE_NAME)
public class User {
  public static final String ID = "_id";
  public static final String TABLE_NAME = "user";

  @PrimaryKey
  @ColumnInfo(name = ID)
  public final int id;

  public final String login;
  public final String avatarUrl;

  public User(int id, String login, String avatarUrl) {
    this.id = id;
    this.login = login;
    this.avatarUrl = avatarUrl;
  }
}
