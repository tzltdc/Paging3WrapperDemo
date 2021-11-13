package io.husayn.paging_library_sample.listing;

import com.jakewharton.rxrelay2.BehaviorRelay;
import io.reactivex.Observable;

class QueryStreamImpl implements QueryStreaming, QueryStream {

  private final BehaviorRelay<PagingQuery> query = BehaviorRelay.create();

  @Override
  public Observable<PagingQuery> streaming() {
    return query.hide();
  }

  @Override
  public void accept(PagingQuery pagingQuery) {
    query.accept(pagingQuery);
  }
}
