package io.stream.paging;

import androidx.paging.PagingData;
import io.reactivex.Observable;
import paging.wrapper.model.data.Pokemon;

public interface PagingDataStreaming {

  Observable<PagingData<Pokemon>> streaming();
}
