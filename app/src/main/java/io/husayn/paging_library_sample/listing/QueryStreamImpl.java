package io.husayn.paging_library_sample.listing;

import com.jakewharton.rxrelay2.BehaviorRelay;
import io.reactivex.Observable;

class QueryStreamImpl implements QueryStreaming, QueryStream {

  private final BehaviorRelay<PagingQueryParam> query =
      BehaviorRelay.createDefault(PagingQueryParam.create(null));

  @Override
  public Observable<PagingQueryParam> streaming() {
    return query.hide();
  }

  @Override
  public void accept(PagingQueryParam pagingQueryParam) {
    query.accept(pagingQueryParam);
  }
}
