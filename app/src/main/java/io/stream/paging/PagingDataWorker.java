package io.stream.paging;

import com.uber.autodispose.ScopeProvider;
import io.husayn.paging_library_sample.AutoDisposeWorker;
import javax.inject.Inject;

public class PagingDataWorker implements AutoDisposeWorker {

  @Inject
  public PagingDataWorker() {}

  @Override
  public void attach(ScopeProvider scopeProvider) {}
}
