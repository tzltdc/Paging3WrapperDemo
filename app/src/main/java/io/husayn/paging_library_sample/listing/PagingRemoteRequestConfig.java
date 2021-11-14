package io.husayn.paging_library_sample.listing;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class PagingRemoteRequestConfig {

  public static final PagingRemoteRequestConfig DEFAULT_QUERY_CONFIG =
      PagingRemoteRequestConfig.create(30);
  public static final PagingRemoteRequestConfig MAX_CONFIG = PagingRemoteRequestConfig.create(1000);

  public abstract int countPerPage();

  public static PagingRemoteRequestConfig create(int countPerPage) {
    return new AutoValue_PagingRemoteRequestConfig(countPerPage);
  }
}
