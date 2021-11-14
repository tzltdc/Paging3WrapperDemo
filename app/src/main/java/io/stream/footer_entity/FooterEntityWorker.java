package io.stream.footer_entity;

import com.uber.autodispose.ScopeProvider;
import io.husayn.paging_library_sample.AutoDisposeWorker;
import javax.inject.Inject;

public class FooterEntityWorker implements AutoDisposeWorker {

  @Inject
  public FooterEntityWorker(FooterEntityStream footerEntityStream) {}

  @Override
  public void attach(ScopeProvider scopeProvider) {}
}
