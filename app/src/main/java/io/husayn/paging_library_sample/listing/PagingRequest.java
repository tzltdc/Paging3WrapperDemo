package io.husayn.paging_library_sample.listing;

class PagingRequest {

  public final long offSet;
  public final PagingQuery pagingQuery;
  public final PagingQueryConfig queryConfig;

  public PagingRequest(long offSet, PagingQuery pagingQuery, PagingQueryConfig queryConfig) {
    this.pagingQuery = pagingQuery;
    this.queryConfig = queryConfig;
    this.offSet = offSet;
  }
}
