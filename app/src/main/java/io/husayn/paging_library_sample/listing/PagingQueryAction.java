package io.husayn.paging_library_sample.listing;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class PagingQueryAction {

  public abstract PagingQueryParam param();

  public abstract LoadType actionType();

  public static PagingQueryAction create(PagingQueryParam param, LoadType loadType) {
    return new AutoValue_PagingQueryAction(param, loadType);
  }

  public enum LoadType {
    REFRESH,
    APPEND
  }
}
