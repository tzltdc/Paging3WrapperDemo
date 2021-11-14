package io.husayn.paging_library_sample.listing;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class PagingRequest {

  public abstract long offSet();

  public abstract PagingQueryParam pagingQueryParam();

  public abstract PagingQueryConfig queryConfig();

  public static PagingRequest create(
      long offSet, PagingQueryParam pagingQueryParam, PagingQueryConfig queryConfig) {
    return new AutoValue_PagingRequest(offSet, pagingQueryParam, queryConfig);
  }
}
