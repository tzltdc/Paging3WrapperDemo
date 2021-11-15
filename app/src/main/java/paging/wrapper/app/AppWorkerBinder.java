package paging.wrapper.app;

import com.google.common.collect.ImmutableSet;
import com.uber.autodispose.ScopeProvider;
import javax.inject.Inject;

public class AppWorkerBinder {

  private final ImmutableSet<AutoDisposeWorker> appWorkerSet;

  @Inject
  public AppWorkerBinder(ImmutableSet<AutoDisposeWorker> appWorkerSet) {

    this.appWorkerSet = appWorkerSet;
  }

  public void attach(ScopeProvider scopeProvider) {
    for (AutoDisposeWorker worker : appWorkerSet) {
      worker.attach(scopeProvider);
    }
  }
}
