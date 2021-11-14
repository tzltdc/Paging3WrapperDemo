package io.stream.paging;

import androidx.paging.PagingData;
import io.husayn.paging_library_sample.data.Pokemon;
import io.reactivex.Observable;

public interface PagingDataStreaming {

  Observable<PagingData<Pokemon>> streaming();
}
