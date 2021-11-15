package paging.wrapper.demo.ui.query;

import com.jakewharton.rxrelay2.BehaviorRelay;
import io.reactivex.Observable;
import paging.wrapper.model.data.PagingQueryContext;
import paging.wrapper.model.data.PagingQueryParam;

public class QueryStreamImpl implements QueryStreaming, QueryStream {

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
