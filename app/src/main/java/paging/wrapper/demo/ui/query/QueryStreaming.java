package paging.wrapper.demo.ui.query;

import io.reactivex.Observable;
import javax.annotation.Nullable;
import paging.wrapper.model.data.FilterBean;
import paging.wrapper.model.data.PagingQueryContext;

public interface QueryStreaming {

  Observable<PagingQueryContext> streaming();

  @Nullable
  FilterBean peek();
}
