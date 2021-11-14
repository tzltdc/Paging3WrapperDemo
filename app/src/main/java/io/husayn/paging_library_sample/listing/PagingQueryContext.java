package io.husayn.paging_library_sample.listing;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class PagingQueryContext {

  public abstract String description();

  public abstract PagingQueryParam param();

  public static PagingQueryContext create(String description, PagingQueryParam param) {
    return new AutoValue_PagingQueryContext(description, param);
  }
}
