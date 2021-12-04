package paging.wrapper.data;

import androidx.annotation.Nullable;
import paging.wrapper.model.data.PageActionResult;
import paging.wrapper.model.data.PagingRemoteRequestConfig;

class NextTargetCountMapper {

  @Nullable
  static Integer nextTargetCount(PageActionResult response) {
    return hasMore(response) ? next(response) : null;
  }

  private static int next(PageActionResult response) {
    return response.request().offSet() + newDataCount(response);
  }

  private static boolean hasMore(PageActionResult response) {
    return actualCount(response) >= expected(response.request().queryConfig());
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