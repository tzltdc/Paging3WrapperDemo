package io.husayn.paging_library_sample.listing;

import androidx.annotation.Nullable;

class PagingQueryMapper {

  public static PagingQuery map(@Nullable String query) {
    return PagingQuery.create(query);
  }
}
