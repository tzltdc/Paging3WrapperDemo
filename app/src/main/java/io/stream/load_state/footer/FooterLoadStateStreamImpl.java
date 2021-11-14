package io.stream.load_state.footer;

import androidx.paging.LoadState;
import com.jakewharton.rxrelay2.BehaviorRelay;
import io.reactivex.Observable;
import javax.inject.Inject;

public class FooterLoadStateStreamImpl implements FooterLoadStateStream, FooterLoadStateStreaming {

  private final BehaviorRelay<LoadState> behaviorRelay = BehaviorRelay.create();

  @Inject
  public FooterLoadStateStreamImpl() {}

  @Override
  public void accept(LoadState loadState) {
    behaviorRelay.accept(loadState);
  }

  @Override
  public Observable<LoadState> streaming() {
    return behaviorRelay.hide();
  }
}
