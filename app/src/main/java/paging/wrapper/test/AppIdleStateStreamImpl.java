package paging.wrapper.test;

import com.jakewharton.rxrelay2.BehaviorRelay;
import io.reactivex.Observable;
import kotlin.Unit;
import timber.log.Timber;

public class AppIdleStateStreamImpl implements AppIdleStateStreaming, AppIdleStateStream {

  private final BehaviorRelay<Unit> relay = BehaviorRelay.create();

  @Override
  public void accept(Unit unit) {
    Timber.i("[22][ui][idle]:Idle state reached");
    relay.accept(unit);
  }

  @Override
  public void clear() {
    Timber.i("IdlingResource AppIdleStateStreamImpl clear.");
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
