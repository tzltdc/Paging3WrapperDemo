package io.husayn.paging_library_sample.listing;

import io.husayn.paging_library_sample.data.Pokemon;

public class PagingRequestMapper {

  public static PagingRequest nextPagingRequest(PagingQuery query, Pokemon lastItem) {
    return PagingRequest.create(
        OffsetHelper.offset(lastItem, query), query, PagingQueryConfig.DEFAULT_QUERY_CONFIG);
  }

  public static PagingRequest defaultPagingRequest(PagingQuery query) {
    return PagingRequest.create(0, query, PagingQueryConfig.DEFAULT_QUERY_CONFIG);
  }
}
