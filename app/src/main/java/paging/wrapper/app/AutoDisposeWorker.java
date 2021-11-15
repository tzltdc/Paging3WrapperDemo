package paging.wrapper.app;

import com.uber.autodispose.ScopeProvider;

public interface AutoDisposeWorker {

  void attach(ScopeProvider scopeProvider);
}
