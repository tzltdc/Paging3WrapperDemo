package paging.wrapper.demo.ui.query;

import paging.wrapper.model.data.PagingQueryContext;

public interface QueryStream {

  void accept(PagingQueryContext context);
}
