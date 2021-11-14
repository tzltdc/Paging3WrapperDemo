package io.husayn.paging_library_sample.listing;

import com.google.auto.value.AutoValue;
import io.husayn.paging_library_sample.listing.PagingQueryAction.LoadType;

@AutoValue
abstract class PagingRequest {

  public abstract LoadType type();

  public abstract long offSet();

  public abstract PagingQueryParam pagingQueryParam();

  public abstract PagingRemoteRequestConfig queryConfig();

  public static PagingRequest create(
      long offSet,
      LoadType loadType,
      PagingQueryParam pagingQueryParam,
      PagingRemoteRequestConfig queryConfig) {
    return new AutoValue_PagingRequest(loadType, offSet, pagingQueryParam, queryConfig);
  }
}
