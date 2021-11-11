package io.husayn.paging_library_sample.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface UserRepoJoinDao {
  @Insert
  void insert(UserRepoJoin userRepoJoin);

  @Query(
      "SELECT * FROM user INNER JOIN user_repo_join ON user._id=user_repo_join.userId WHERE user_repo_join.repoId=:repoId")
  List<User> getUsersForRepository(final int repoId);

  @Query(
      "SELECT * FROM repo INNER JOIN user_repo_join ON repo._id=user_repo_join.repoId WHERE user_repo_join.userId=:userId")
  List<Repo> getRepositoriesForUsers(final int userId);
}
