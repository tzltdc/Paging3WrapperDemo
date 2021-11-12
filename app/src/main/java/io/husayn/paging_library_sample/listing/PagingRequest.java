package io.husayn.paging_library_sample.listing;

class PagingRequest {

  public final int offSet;
  public final int countPerPage;

  public PagingRequest(int offSet, int countPerPage) {
    this.countPerPage = countPerPage;
    this.offSet = offSet;
  }
}
