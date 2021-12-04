package paging.wrapper.data;

import androidx.annotation.Nullable;
import paging.wrapper.model.data.PageActionResult;
import paging.wrapper.model.data.PagingRemoteRequestConfig;
import timber.log.Timber;

class NextTargetCountMapper {

  @Nullable
  static Integer nextTargetCount(PageActionResult response) {
    return hasMore(response) ? next(response) : null;
  }

  private static int next(PageActionResult response) {
    return response.request().offSet() + newDataCount(response);
  }

  private static boolean hasMore(PageActionResult response) {
    int actualCount = actualCount(response);
    int targetCount = expected(response.request().queryConfig());
    boolean result = actualCount >= targetCount;
    Timber.i("[remote]:actual:%s, target:%s, hasMore:%s", actualCount, targetCount, result);
    return result;
  }

  private static int expected(PagingRemoteRequestConfig config) {
    return config.countPerPage();
  }

  private static int actualCount(PageActionResult response) {
    return newDataCount(response);
  }

  private static int newDataCount(PageActionResult response) {
    return response.response().list().size();
  }
}
