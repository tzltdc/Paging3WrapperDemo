package io.husayn.paging_library_sample.listing;

import com.google.auto.value.AutoValue;
import io.husayn.paging_library_sample.listing.PagingQueryAction.LoadType;

@AutoValue
abstract class PagingAction {

  public abstract LoadType type();

  public abstract PageActionResult data();

  public static PagingAction create(LoadType loadType, PageActionResult data) {
    return new AutoValue_PagingAction(loadType, data);
  }
}
