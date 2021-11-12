package io.husayn.paging_library_sample.listing;

class PagingQuery {

  public final String searchKey;
  public final Boolean desc;

  public PagingQuery(String searchKey, Boolean desc) {
    this.searchKey = searchKey;
    this.desc = desc;
  }
}
