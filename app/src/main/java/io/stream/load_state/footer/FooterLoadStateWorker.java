package io.stream.load_state.footer;

import com.uber.autodispose.ScopeProvider;
import io.husayn.paging_library_sample.AutoDisposeWorker;
import javax.inject.Inject;

public class FooterLoadStateWorker implements AutoDisposeWorker {

  @Inject
  public FooterLoadStateWorker() {}

  @Override
  public void attach(ScopeProvider scopeProvider) {}
}
