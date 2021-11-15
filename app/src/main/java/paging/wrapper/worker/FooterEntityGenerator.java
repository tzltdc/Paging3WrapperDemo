package paging.wrapper.worker;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import com.uber.autodispose.ScopeProvider;
import javax.inject.Inject;
import paging.wrapper.app.AutoDisposeWorker;
import paging.wrapper.stream.FooterEntityStream;
import paging.wrapper.stream.RawFooterEntityStreaming;

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
