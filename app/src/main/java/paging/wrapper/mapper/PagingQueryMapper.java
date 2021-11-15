package paging.wrapper.mapper;

import androidx.annotation.Nullable;
import paging.wrapper.model.data.PagingQueryParam;

public class PagingQueryMapper {

  public static PagingQueryParam map(@Nullable String query) {
    return PagingQueryParam.create(query);
  }
}
