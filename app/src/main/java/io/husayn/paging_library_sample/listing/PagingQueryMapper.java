package io.husayn.paging_library_sample.listing;

import androidx.annotation.Nullable;

class PagingQueryMapper {

  public static PagingQueryParam map(@Nullable String query) {
    return PagingQueryParam.create(query);
  }
}
