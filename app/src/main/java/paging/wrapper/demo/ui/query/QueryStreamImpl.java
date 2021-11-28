package paging.wrapper.demo.ui.query;

import androidx.annotation.Nullable;
import com.jakewharton.rxrelay2.BehaviorRelay;
import io.reactivex.Observable;
import paging.wrapper.mapper.PagingQueryMapper;
import paging.wrapper.model.data.FilterBean;
import paging.wrapper.model.data.PagingQueryContext;

public class QueryStreamImpl implements QueryStreaming, QueryStream {

  private final BehaviorRelay<FilterBean> query = BehaviorRelay.create();

  @Override
  public Observable<PagingQueryContext> streaming() {
    return query.hide().map(this::pagingQueryContext);
  }

  @Nullable
  @Override
  public FilterBean peek() {
    return query.getValue();
  }

  private PagingQueryContext pagingQueryContext(FilterBean bean) {
    return PagingQueryContext.create(bean.description(), PagingQueryMapper.map(bean.value()));
  }

  @Override
  public void accept(FilterBean bean) {
    query.accept(bean);
  }
}
