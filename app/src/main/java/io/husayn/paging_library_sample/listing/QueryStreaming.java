package io.husayn.paging_library_sample.listing;

import io.reactivex.Observable;
import paging.wrapper.model.data.PagingQueryContext;

interface QueryStreaming {

  Observable<PagingQueryContext> streaming();
}
