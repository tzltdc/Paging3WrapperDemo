package paging.wrapper.stream;

import androidx.paging.CombinedLoadStates;
import androidx.paging.LoadState;
import com.jakewharton.rxrelay2.BehaviorRelay;
import io.reactivex.Observable;
import javax.inject.Inject;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import paging.wrapper.di.app.ActivityScope;

@ActivityScope
public class CombinedLoadStatesCallback
    implements Function1<CombinedLoadStates, Unit>, LoadStateStreaming {

  private final BehaviorRelay<CombinedLoadStates> behaviorRelay = BehaviorRelay.create();

  @Inject
  public CombinedLoadStatesCallback() {}

  @Override
  public Unit invoke(CombinedLoadStates states) {
    behaviorRelay.accept(states);
    return Unit.INSTANCE;
  }

  @Override
  public Observable<LoadState> footer() {
    return behaviorRelay.hide().map(CombinedLoadStates::getAppend).distinctUntilChanged();
  }

  @Override
  public Observable<LoadState> header() {
    return behaviorRelay.hide().map(CombinedLoadStates::getRefresh).distinctUntilChanged();
  }
}
