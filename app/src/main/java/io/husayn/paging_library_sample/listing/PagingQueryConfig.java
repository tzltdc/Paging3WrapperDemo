package io.husayn.paging_library_sample.listing;

class PagingQueryConfig {

  public static final PagingQueryConfig DEFAULT_QUERY_CONFIG = new PagingQueryConfig(10);
  public final int countPerPage;

  public PagingQueryConfig(int countPerPage) {
    this.countPerPage = countPerPage;
  }
}
