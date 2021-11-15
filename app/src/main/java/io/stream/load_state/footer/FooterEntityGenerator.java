package io.stream.load_state.footer;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import com.uber.autodispose.ScopeProvider;
import io.husayn.paging_library_sample.AutoDisposeWorker;
import io.stream.footer_entity.FooterEntityStream;
import javax.inject.Inject;

public class FooterEntityGenerator implements AutoDisposeWorker {

  private final FooterEntityStream footerEntityStream;
  private final RawFooterEntityStreaming rawFooterEntityStreaming;

  @Inject
  public FooterEntityGenerator(
      FooterEntityStream footerEntityStream, RawFooterEntityStreaming rawFooterEntityStreaming) {
    this.footerEntityStream = footerEntityStream;
    this.rawFooterEntityStreaming = rawFooterEntityStreaming;
  }

  @Override
  public void attach(ScopeProvider scopeProvider) {
    rawFooterEntityStreaming
        .streaming()
        .as(autoDisposable(scopeProvider))
        .subscribe(footerEntityStream::accept);
  }
}
