package paging.wrapper.data;

import androidx.paging.PagingSource.LoadParams;

public class ToStringUtil {

  public static String toString(LoadParams<Integer> params) {
    return String.format(
        "LoadParams-> key:%s, loadSize:%s, action:%s, placeholderEnabled:%s",
        params.getKey(),
        params.getLoadSize(),
        params.getClass().getSimpleName(),
        params.getPlaceholdersEnabled());
  }

  public static String toString(RemotePagingResponse data) {
    return String.format("nextKey:%s,data:%s", data.nextTargetCount(), data.response().list());
  }
}
