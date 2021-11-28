package paging.wrapper.test;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import com.uber.autodispose.ScopeProvider;
import javax.inject.Inject;
import paging.wrapper.app.AutoDisposeWorker;
import paging.wrapper.stream.LoadStateStreaming;

public class AppIdleStateSourceWorker implements AutoDisposeWorker {

  private final LoadStateStreaming loadStateStreaming;
  private final AppIdleStateStream appIdleStateStream;

  @Inject
  public AppIdleStateSourceWorker(
      LoadStateStreaming loadStateStreaming, AppIdleStateStream appIdleStateStream) {
    this.loadStateStreaming = loadStateStreaming;
    this.appIdleStateStream = appIdleStateStream;
  }

  @Override
  public void attach(ScopeProvider scopeProvider) {

    loadStateStreaming
        .idle()
        .as(autoDisposable(scopeProvider))
        .subscribe(appIdleStateStream::accept);
  }
}
