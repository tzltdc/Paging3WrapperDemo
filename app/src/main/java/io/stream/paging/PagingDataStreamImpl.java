package io.stream.paging;

import androidx.paging.PagingData;
import com.jakewharton.rxrelay2.BehaviorRelay;
import io.husayn.paging_library_sample.data.Pokemon;
import io.reactivex.Observable;
import javax.inject.Inject;

public class PagingDataStreamImpl implements PagingDataStream, PagingDataStreaming {

  private final BehaviorRelay<PagingData<Pokemon>> behaviorRelay = BehaviorRelay.create();

  @Inject
  public PagingDataStreamImpl() {}

  @Override
  public void accept(PagingData<Pokemon> data) {
    behaviorRelay.accept(data);
  }

  @Override
  public Observable<PagingData<Pokemon>> streaming() {
    return behaviorRelay.hide();
  }
}
