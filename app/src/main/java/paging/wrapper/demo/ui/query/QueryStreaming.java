package paging.wrapper.demo.ui.query;

import io.reactivex.Observable;
import paging.wrapper.model.data.PagingQueryContext;

public interface QueryStreaming {

  Observable<PagingQueryContext> streaming();
}
