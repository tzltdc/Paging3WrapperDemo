package paging.wrapper.data;

import androidx.annotation.NonNull;
import androidx.paging.PagingSource.LoadParams;
import paging.wrapper.model.data.PagingQueryContext;
import paging.wrapper.model.data.PagingQueryParam;
import paging.wrapper.model.data.PagingRemoteRequestConfig;
import paging.wrapper.model.data.PagingRequest;

class RemoteLoadRequestMapper {

  @NonNull
  static PagingRequest getPagingRequest(
      @NonNull LoadParams<Integer> params, PagingQueryContext query) {
    return nextPagingRequest(loadedCount(params), query.param());
  }

  private static PagingRequest nextPagingRequest(int loadedCount, PagingQueryParam query) {
    return PagingRequest.create(loadedCount, query, PagingRemoteRequestConfig.DEFAULT_QUERY_CONFIG);
  }

  private static int loadedCount(LoadParams<Integer> params) {
    Integer targetLoadCount = params.getKey();
    if (targetLoadCount == null) {
      return 0;
    } else {
      return targetLoadCount;
    }
  }
}
