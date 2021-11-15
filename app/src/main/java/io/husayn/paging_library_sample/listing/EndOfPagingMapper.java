package io.husayn.paging_library_sample.listing;

import paging.wrapper.model.data.PageActionResult;
import timber.log.Timber;

class EndOfPagingMapper {

  public static boolean endOfPaging(PageActionResult data) {
    int requested = data.request().queryConfig().countPerPage();
    int responded = data.response().list().size();
    boolean endOfPaging = requested > responded;
    Timber.i("endOfPaging:%s, requested:%s, responded:%s", endOfPaging, requested, responded);
    return endOfPaging;
  }
}
