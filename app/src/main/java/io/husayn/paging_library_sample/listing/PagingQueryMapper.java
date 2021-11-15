package io.husayn.paging_library_sample.listing;

import androidx.annotation.Nullable;
import paging.wrapper.model.data.PagingQueryParam;

class PagingQueryMapper {

  public static PagingQueryParam map(@Nullable String query) {
    return PagingQueryParam.create(query);
  }
}
