package io.husayn.paging_library_sample.listing;

class PagingRequest {

  public final int offSet;
  public final PagingQueryConfig pagingQueryConfig;

  public PagingRequest(int offSet, PagingQueryConfig pagingQueryConfig) {
    this.pagingQueryConfig = pagingQueryConfig;
    this.offSet = offSet;
  }
}
