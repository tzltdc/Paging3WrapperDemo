package paging.wrapper.mapper;

import javax.inject.Inject;
import paging.wrapper.model.data.PagingQueryParam;
import paging.wrapper.model.data.PagingRemoteRequestConfig;
import paging.wrapper.model.data.PagingRequest;
import paging.wrapper.model.data.Pokemon;

public class PagingRequestMapper {

  private final OffsetHelper offsetHelper;

  @Inject
  public PagingRequestMapper(OffsetHelper offsetHelper) {
    this.offsetHelper = offsetHelper;
  }

  public PagingRequest nextPagingRequest(PagingQueryParam query, Pokemon lastItem) {
    return PagingRequest.create(
        offsetHelper.offset(lastItem, query),
        query,
        PagingRemoteRequestConfig.DEFAULT_QUERY_CONFIG);
  }

  public static PagingRequest defaultPagingRequest(PagingQueryParam query) {
    return PagingRequest.create(0, query, PagingRemoteRequestConfig.DEFAULT_QUERY_CONFIG);
  }
}
