package io.husayn.paging_library_sample.listing;

import io.husayn.paging_library_sample.listing.PagingAction.Data;
import timber.log.Timber;

class EndOfPagingMapper {

  public static boolean endOfPaging(Data data) {
    int requested = data.request().queryConfig().countPerPage();
    int responded = data.response().list().size();
    boolean endOfPaging = requested > responded;
    Timber.i("tonny endOfPaging:%s, requested:%s, responded:%s", endOfPaging, requested, responded);
    return endOfPaging;
  }
}
