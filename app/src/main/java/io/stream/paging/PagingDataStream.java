package io.stream.paging;

import androidx.paging.PagingData;
import paging.wrapper.model.data.Pokemon;

public interface PagingDataStream {

  void accept(PagingData<Pokemon> pagingData);
}
