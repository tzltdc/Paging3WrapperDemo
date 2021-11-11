package io.husayn.paging_library_sample.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = Repo.TABLE_NAME)
public class Repo {
  public static final String TABLE_NAME = "repo";
  public static final String ID = "_id";

  @PrimaryKey
  @ColumnInfo(name = ID)
  public final int id;

  public final String name;
  public final String url;

  public Repo(int id, String name, String url) {
    this.id = id;
    this.name = name;
    this.url = url;
  }
}
