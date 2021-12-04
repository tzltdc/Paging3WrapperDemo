package paging.wrapper.data;

import androidx.paging.PagingSource.LoadResult;
import paging.wrapper.model.data.Pokemon;
import timber.log.Timber;

class RemoteLoadResultMapper {

  static LoadResult<Integer, Pokemon> asLoadResult(RemotePagingResponse response) {
    Timber.i("[remote]:loadResult:%s", ToStringUtil.toString(response));
    return new LoadResult.Page<>(
        response.response().list(),
        null,
        response.nextTargetCount(),
        LoadResult.Page.COUNT_UNDEFINED,
        LoadResult.Page.COUNT_UNDEFINED);
  }
}
