package paging.wrapper.test;

import com.google.common.base.Optional;
import com.jakewharton.rxrelay2.BehaviorRelay;
import io.reactivex.Observable;
import kotlin.Unit;
import timber.log.Timber;

public class AppIdleStateStreamImpl implements AppIdleStateStreaming, AppIdleStateStream {

  private final BehaviorRelay<Optional<Unit>> relay = BehaviorRelay.create();

  @Override
  public void accept(Unit unit) {
    Timber.i("IdlingResource AppIdleStateStreamImpl accept:%s", unit);
    relay.accept(Optional.of(unit));
  }

  @Override
  public void clear() {
    Timber.i("IdlingResource AppIdleStateStreamImpl clear.");
    relay.accept(Optional.absent());
  }

  @Override
  public Observable<Unit> idling() {
    return relay.hide().filter(Optional::isPresent).map(Optional::get);
  }

  @Override
  public boolean peek() {
    Optional<Unit> value = relay.getValue();
    return value != null && value.isPresent();
  }
}
