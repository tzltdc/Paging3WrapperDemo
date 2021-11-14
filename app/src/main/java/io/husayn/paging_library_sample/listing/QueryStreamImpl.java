package io.husayn.paging_library_sample.listing;

import com.jakewharton.rxrelay2.BehaviorRelay;
import io.reactivex.Observable;

class QueryStreamImpl implements QueryStreaming, QueryStream {

  private final BehaviorRelay<PagingQueryContext> query =
      BehaviorRelay.createDefault(
          PagingQueryContext.create(FilterOptionProvider.ALL, PagingQueryParam.create(null)));

  @Override
  public Observable<PagingQueryContext> streaming() {
    return query.hide();
  }

  @Override
  public void accept(PagingQueryContext pagingQueryParam) {
    query.accept(pagingQueryParam);
  }
}
