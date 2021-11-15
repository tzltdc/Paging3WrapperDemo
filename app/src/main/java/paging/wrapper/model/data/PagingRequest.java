package paging.wrapper.model.data;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class PagingRequest {

  public abstract long offSet();

  public abstract PagingQueryParam pagingQueryParam();

  public abstract PagingRemoteRequestConfig queryConfig();

  public static PagingRequest create(
      long offSet, PagingQueryParam pagingQueryParam, PagingRemoteRequestConfig queryConfig) {
    return new AutoValue_PagingRequest(offSet, pagingQueryParam, queryConfig);
  }
}
