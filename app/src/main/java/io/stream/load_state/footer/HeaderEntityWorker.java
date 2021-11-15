package io.stream.load_state.footer;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import com.google.common.base.Optional;
import com.uber.autodispose.ScopeProvider;
import io.husayn.paging_library_sample.AutoDisposeWorker;
import io.husayn.paging_library_sample.listing.HeaderContract;
import io.view.header.HeaderEntity;
import javax.inject.Inject;

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
