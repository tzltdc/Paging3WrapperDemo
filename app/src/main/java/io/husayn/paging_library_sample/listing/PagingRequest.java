package io.husayn.paging_library_sample.listing;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class PagingRequest {

  public abstract long offSet();

  public abstract PagingQuery pagingQuery();

  public abstract PagingQueryConfig queryConfig();

  public static PagingRequest create(
      long offSet, PagingQuery pagingQuery, PagingQueryConfig queryConfig) {
    return new AutoValue_PagingRequest(offSet, pagingQuery, queryConfig);
  }
}
