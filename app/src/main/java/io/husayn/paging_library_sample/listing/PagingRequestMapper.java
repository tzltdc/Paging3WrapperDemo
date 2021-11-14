package io.husayn.paging_library_sample.listing;

import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.listing.PagingQueryAction.LoadType;

public class PagingRequestMapper {

  public static PagingRequest nextPagingRequest(PagingQueryParam query, Pokemon lastItem) {
    return PagingRequest.create(
        OffsetHelper.offset(lastItem, query),
        LoadType.REFRESH,
        query,
        PagingRemoteRequestConfig.DEFAULT_QUERY_CONFIG);
  }

  public static PagingRequest defaultPagingRequest(PagingQueryParam query) {
    return PagingRequest.create(
        0, LoadType.APPEND, query, PagingRemoteRequestConfig.DEFAULT_QUERY_CONFIG);
  }
}
