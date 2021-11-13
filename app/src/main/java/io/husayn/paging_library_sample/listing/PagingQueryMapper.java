package io.husayn.paging_library_sample.listing;

class PagingQueryMapper {

  public static PagingQuery map(String query) {
    return PagingQuery.create(query.equals(FilterOptionProvider.EMPTY) ? null : query);
  }
}
