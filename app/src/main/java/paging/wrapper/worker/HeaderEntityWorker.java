package paging.wrapper.worker;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import com.google.common.base.Optional;
import com.uber.autodispose.ScopeProvider;
import javax.inject.Inject;
import paging.wrapper.app.AutoDisposeWorker;
import paging.wrapper.contract.HeaderContract;
import paging.wrapper.model.ui.HeaderEntity;
import paging.wrapper.stream.HeaderEntityStreaming;

public class HeaderEntityWorker implements AutoDisposeWorker {

  private final HeaderContract headerContract;
  private final HeaderEntityStreaming headerEntityStreaming;

  @Inject
  public HeaderEntityWorker(
      HeaderContract headerContract, HeaderEntityStreaming headerEntityStreaming) {
    this.headerContract = headerContract;
    this.headerEntityStreaming = headerEntityStreaming;
  }

  @Override
  public void attach(ScopeProvider scopeProvider) {
    headerEntityStreaming.streaming().as(autoDisposable(scopeProvider)).subscribe(this::bind);
  }

  private void bind(Optional<HeaderEntity> entityOptional) {
    headerContract.bind(entityOptional.orNull());
  }
}