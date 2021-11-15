package io.husayn.paging_library_sample.listing;

import paging.wrapper.model.data.PagingQueryContext;

interface QueryStream {

  void accept(PagingQueryContext context);
}
