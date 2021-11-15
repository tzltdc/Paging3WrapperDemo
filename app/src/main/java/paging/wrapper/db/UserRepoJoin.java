package paging.wrapper.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
    tableName = UserRepoJoin.USER_REPO_JOIN,
    primaryKeys = {UserRepoJoin.USER_ID, UserRepoJoin.REPO_ID},
    foreignKeys = {
      @ForeignKey(
          entity = User.class,
          parentColumns = User.ID,
          childColumns = UserRepoJoin.USER_ID),
      @ForeignKey(entity = Repo.class, parentColumns = Repo.ID, childColumns = UserRepoJoin.REPO_ID)
    })
public class UserRepoJoin {

  public static final String USER_ID = "userId";
  public static final String USER_REPO_JOIN = "user_repo_join";
  public static final String REPO_ID = "repoId";

  @ColumnInfo(name = USER_ID)
  public final int userId;

  @ColumnInfo(name = REPO_ID)
  public final int repoId;

  public UserRepoJoin(final int userId, final int repoId) {
    this.userId = userId;
    this.repoId = repoId;
  }
}
