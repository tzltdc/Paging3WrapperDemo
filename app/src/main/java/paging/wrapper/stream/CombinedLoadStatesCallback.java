package paging.wrapper.stream;

import androidx.annotation.NonNull;
import androidx.pagingx.CombinedLoadStates;
import androidx.pagingx.CombinedLoadStatesMapper;
import androidx.pagingx.LoadState;
import com.jakewharton.rxrelay2.BehaviorRelay;
import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import paging.wrapper.di.app.ActivityScope;
import paging.wrapper.di.thread.AppScheduler;
import timber.log.Timber;

@ActivityScope
public class CombinedLoadStatesCallback
    implements Function1<androidx.paging.CombinedLoadStates, Unit>, LoadStateStreaming {

  private final BehaviorRelay<CombinedLoadStates> behaviorRelay = BehaviorRelay.create();
  private AppScheduler appScheduler;

  @Inject
  public CombinedLoadStatesCallback(AppScheduler appScheduler) {
    this.appScheduler = appScheduler;
  }

  /**
   * This method will be called excessively from paging internal library. And its behavior is quite
   * chaotic(One could refer to the raw_log.md as references).
   *
   * <p>What is known is that
   *
   * <p>1, whenever the data loading action is concluded, there will be at least a
   * [CombinedLoadStates] that all sub state are NotLoading. 2, Such state could be duplicated.
   *
   * <p>Based on these learnings, we are to introduce allIdle
   */
  @Override
  public Unit invoke(androidx.paging.CombinedLoadStates states) {
    CombinedLoadStates wrap = CombinedLoadStatesMapper.wrap(states);
    log(wrap);
    behaviorRelay.accept(wrap);
    return Unit.INSTANCE;
  }

  private void log(CombinedLoadStates states) {
    Timber.i("onLoadStateChanged:combinedLoadStates:%s", states);
    if (LoadingStateIdleMapper.allIdle(states)) {
      logIdle(states);
    }
  }

  @Override
  public Observable<LoadState> footer() {
    return behaviorRelay.hide().map(CombinedLoadStates::getAppend).distinctUntilChanged();
  }

  @Override
  public Observable<LoadState> header() {
    return behaviorRelay.hide().map(CombinedLoadStates::getRefresh).distinctUntilChanged();
  }

  @Override
  public Observable<Unit> idle() {
    return behaviorRelay
        .hide()
        .filter(LoadingStateIdleMapper::allIdle)
        .compose(this::throttle)
        .map(state -> Unit.INSTANCE)
        .doOnNext(this::logIdle);
  }

  @Override
  public Observable<CombinedLoadStates> raw() {
    return behaviorRelay.hide();
  }

  /**
   * Two reasons to throttle the signals :
   *
   * <p>1, {@link #invoke} could be called with duplicated items. Throttling the signal to
   * deduplicate the signal.
   *
   * <p>2, The espresso unit test could use a gap to wait the UI thread render the data.
   */
  @NonNull
  private Observable<CombinedLoadStates> throttle(Observable<CombinedLoadStates> upstream) {
    return upstream.throttleLast(350, TimeUnit.MILLISECONDS, appScheduler.worker());
  }

  private void logIdle(Unit unit) {
    Timber.i("onLoadStateChanged: IDLE signal emitted for consumers");
  }

  private void logIdle(CombinedLoadStates states) {
    Timber.i("onLoadStateChanged:combinedLoadStates is becoming idle:%s", states);
  }
}
