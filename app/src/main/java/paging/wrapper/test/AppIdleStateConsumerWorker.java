package paging.wrapper.test;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import androidx.test.espresso.IdlingResource;
import com.uber.autodispose.ScopeProvider;
import javax.inject.Inject;
import kotlin.Unit;
import paging.wrapper.app.AutoDisposeWorker;
import paging.wrapper.di.app.AppScope;

@AppScope
public class AppIdleStateConsumerWorker implements AutoDisposeWorker, IdlingResource {

  private final AppIdleStateStreaming appIdleStateStreaming;
  private ResourceCallback resourceCallback;

  @Inject
  public AppIdleStateConsumerWorker(AppIdleStateStreaming appIdleStateStreaming) {
    this.appIdleStateStreaming = appIdleStateStreaming;
  }

  @Override
  public void attach(ScopeProvider scopeProvider) {
    appIdleStateStreaming.idling().as(autoDisposable(scopeProvider)).subscribe(this::accept);
  }

  private void accept(Unit unit) {
    if (resourceCallback != null) {
      resourceCallback.onTransitionToIdle();
    }
  }

  @Override
  public String getName() {
    return "AppIdleState";
  }

  @Override
  public boolean isIdleNow() {
    return appIdleStateStreaming.peek();
  }

  @Override
  public void registerIdleTransitionCallback(ResourceCallback callback) {
    this.resourceCallback = callback;
  }
}
