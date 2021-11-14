package io.stream.paging;

import androidx.paging.PagingData;
import io.husayn.paging_library_sample.data.Pokemon;

public interface PagingDataStream {

  void accept(PagingData<Pokemon> pagingData);
}
