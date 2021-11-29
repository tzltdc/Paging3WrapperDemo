package paging.wrapper.test;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import androidx.test.espresso.IdlingResource;
import com.uber.autodispose.ScopeProvider;
import javax.inject.Inject;
import kotlin.Unit;
import paging.wrapper.app.AutoDisposeWorker;
import timber.log.Timber;

public class AppIdleStateConsumerWorker implements AutoDisposeWorker, IdlingResource {

  private final AppIdleStateStream appIdleStateStream;
  private final AppIdleStateStreaming appIdleStateStreaming;
  private ResourceCallback resourceCallback;

  @Inject
  public AppIdleStateConsumerWorker(
      AppIdleStateStream appIdleStateStream, AppIdleStateStreaming appIdleStateStreaming) {
    this.appIdleStateStream = appIdleStateStream;
    this.appIdleStateStreaming = appIdleStateStreaming;
    Timber.i("IdlingResource created :%s", this);
  }

  @Override
  public void attach(ScopeProvider scopeProvider) {
    Timber.i("IdlingResource attach:%s", this);
    appIdleStateStreaming.idling().as(autoDisposable(scopeProvider)).subscribe(this::accept);
  }

  private void accept(Unit unit) {
    if (resourceCallback != null) {
      Timber.i("IdlingResource onTransitionToIdle:%s", this);
      resourceCallback.onTransitionToIdle();
    } else {
      Timber.w("IdlingResource skipped onTransitionToIdle:%s", this);
    }
  }

  @Override
  public String getName() {
    return "AppIdleState";
  }

  @Override
  public boolean isIdleNow() {
    boolean idle = appIdleStateStreaming.peek();
    Timber.i("IdlingResource isIdleNow:%s", idle);
    return idle;
  }

  @Override
  public void registerIdleTransitionCallback(ResourceCallback callback) {
    appIdleStateStream.clear();
    Timber.i("IdlingResource registerIdleTransitionCallback:%s", callback);
    this.resourceCallback = callback;
  }
}
