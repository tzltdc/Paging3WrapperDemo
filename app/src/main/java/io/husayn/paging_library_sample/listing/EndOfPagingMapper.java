package io.husayn.paging_library_sample.listing;

class EndOfPagingMapper {

  public static boolean endOfPaging(PagingAction action) {
    return action.request().queryConfig().countPerPage() > action.response().list().size();
  }
}
