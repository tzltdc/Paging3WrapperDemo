package paging.wrapper.stream;

import androidx.paging.PagingData;
import io.reactivex.Observable;
import paging.wrapper.model.data.Pokemon;

public interface PagingDataStreaming {

  Observable<PagingData<Pokemon>> streaming();
}
