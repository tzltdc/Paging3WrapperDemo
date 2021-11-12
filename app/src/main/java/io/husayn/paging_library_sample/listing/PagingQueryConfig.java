package io.husayn.paging_library_sample.listing;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class PagingQueryConfig {

  public static final PagingQueryConfig DEFAULT_QUERY_CONFIG = PagingQueryConfig.create(10);
  public static final PagingQueryConfig MAX_CONFIG = PagingQueryConfig.create(1000);

  public abstract int countPerPage();

  public static PagingQueryConfig create(int countPerPage) {
    return new AutoValue_PagingQueryConfig(countPerPage);
  }
}
