package paging.wrapper.data;

import androidx.paging.PagingSource.LoadResult;
import paging.wrapper.model.data.Pokemon;

class RemoteLoadResultMapper {

  static LoadResult<Integer, Pokemon> asLoadResult(RemotePagingResponse response) {
    return new LoadResult.Page<>(
        response.response().list(),
        null,
        response.nextTargetCount(),
        LoadResult.Page.COUNT_UNDEFINED,
        LoadResult.Page.COUNT_UNDEFINED);
  }
}