package paging.wrapper.demo.ui.query;

import paging.wrapper.model.data.FilterBean;

public interface QueryStream {

  void accept(FilterBean context);
}
