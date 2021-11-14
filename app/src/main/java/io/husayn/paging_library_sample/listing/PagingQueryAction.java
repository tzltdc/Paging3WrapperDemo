package io.husayn.paging_library_sample.listing;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class PagingQueryAction {

  public abstract PagingQueryParam param();

  public abstract ActionType actionType();

  public static PagingQueryAction create(PagingQueryParam param, ActionType actionType) {
    return new AutoValue_PagingQueryAction(param, actionType);
  }

  public enum ActionType {
    INITIAL_LOAD,
    LOAD_MORE
  }
}
