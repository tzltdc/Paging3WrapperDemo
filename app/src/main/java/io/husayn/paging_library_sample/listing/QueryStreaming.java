package io.husayn.paging_library_sample.listing;

import io.reactivex.Observable;

interface QueryStreaming {

  Observable<PagingQueryParam> streaming();
}
