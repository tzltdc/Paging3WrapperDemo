package paging.wrapper.test;

import com.jakewharton.rxrelay2.BehaviorRelay;
import io.reactivex.Observable;
import kotlin.Unit;

public class AppIdleStateStreamImpl implements AppIdleStateStreaming, AppIdleStateStream {

  private final BehaviorRelay<Unit> relay = BehaviorRelay.create();

  @Override
  public void accept(Unit unit) {
    relay.accept(unit);
  }

  @Override
  public Observable<Unit> idling() {
    return relay.hide();
  }

  @Override
  public boolean peek() {
    return relay.getValue() != null;
  }
}
