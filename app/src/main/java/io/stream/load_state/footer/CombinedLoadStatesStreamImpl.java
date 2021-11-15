package io.stream.load_state.footer;

import androidx.paging.CombinedLoadStates;
import androidx.paging.LoadState;
import com.jakewharton.rxrelay2.BehaviorRelay;
import io.reactivex.Observable;
import javax.inject.Inject;

public class CombinedLoadStatesStreamImpl
    implements CombinedLoadStatesStream, FooterLoadStateStreaming {

  private final BehaviorRelay<CombinedLoadStates> behaviorRelay = BehaviorRelay.create();

  @Inject
  public CombinedLoadStatesStreamImpl() {}

  @Override
  public void accept(CombinedLoadStates combinedLoadStates) {
    behaviorRelay.accept(combinedLoadStates);
  }

  @Override
  public Observable<LoadState> streaming() {
    return behaviorRelay.hide().map(CombinedLoadStates::getAppend).distinctUntilChanged();
  }
}
