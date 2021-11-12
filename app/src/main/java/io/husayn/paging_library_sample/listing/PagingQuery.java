package io.husayn.paging_library_sample.listing;

import androidx.annotation.Nullable;
import com.google.auto.value.AutoValue;

@AutoValue
abstract class PagingQuery {

  @Nullable
  public abstract String searchKey();

  public static PagingQuery create(String newSearchKey) {
    return new AutoValue_PagingQuery(newSearchKey);
  }
}
