package io.husayn.paging_library_sample;

import com.uber.autodispose.ScopeProvider;

public interface AutoDisposeWorker {

  void attach(ScopeProvider scopeProvider);
}
