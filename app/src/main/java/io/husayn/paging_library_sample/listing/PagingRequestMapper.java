package io.husayn.paging_library_sample.listing;

import paging.wrapper.model.data.PagingQueryParam;
import paging.wrapper.model.data.PagingRemoteRequestConfig;
import paging.wrapper.model.data.PagingRequest;
import paging.wrapper.model.data.Pokemon;

public class PagingRequestMapper {

  public static PagingRequest nextPagingRequest(PagingQueryParam query, Pokemon lastItem) {
    return PagingRequest.create(
        OffsetHelper.offset(lastItem, query),
        query,
        PagingRemoteRequestConfig.DEFAULT_QUERY_CONFIG);
  }

  public static PagingRequest defaultPagingRequest(PagingQueryParam query) {
    return PagingRequest.create(0, query, PagingRemoteRequestConfig.DEFAULT_QUERY_CONFIG);
  }
}
