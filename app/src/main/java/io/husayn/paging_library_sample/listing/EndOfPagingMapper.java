package io.husayn.paging_library_sample.listing;

import timber.log.Timber;

class EndOfPagingMapper {

  public static boolean endOfPaging(PagingAction action) {
    int requested = action.request().queryConfig().countPerPage();
    int responded = action.response().list().size();
    boolean endOfPaging = requested > responded;
    Timber.i("endOfPaging:%s, requested:%s, responded:%s", endOfPaging, requested, responded);
    return endOfPaging;
  }
}
