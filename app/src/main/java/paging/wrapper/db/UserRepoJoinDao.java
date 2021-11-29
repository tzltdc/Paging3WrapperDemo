package paging.wrapper.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface UserRepoJoinDao {
  @Insert
  void insert(UserRepoJoin userRepoJoin);

  @Query(
      "SELECT _id, login, avatarUrl FROM user INNER JOIN user_repo_join ON user._id=user_repo_join.userId WHERE user_repo_join.repoId=:repoId")
  List<User> getUsersForRepository(final int repoId);

  @Query(
      "SELECT _id, name, url  FROM repo INNER JOIN user_repo_join ON repo._id=user_repo_join.repoId WHERE user_repo_join.userId=:userId")
  List<Repo> getRepositoriesForUsers(final int userId);
}
