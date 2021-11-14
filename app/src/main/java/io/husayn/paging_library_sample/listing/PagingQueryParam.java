package io.husayn.paging_library_sample.listing;

import androidx.annotation.Nullable;
import com.google.auto.value.AutoValue;

@AutoValue
abstract class PagingQueryParam {

  @Nullable
  public abstract String searchKey();

  public static PagingQueryParam create(String newSearchKey) {
    return new AutoValue_PagingQueryParam(newSearchKey);
  }
}
