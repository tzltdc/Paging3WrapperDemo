package io.stream.load_state.footer;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import androidx.paging.LoadState;
import com.google.common.base.Optional;
import com.uber.autodispose.ScopeProvider;
import io.husayn.paging_library_sample.AutoDisposeWorker;
import io.husayn.paging_library_sample.listing.PagingViewModel;
import io.husayn.paging_library_sample.listing.StateMapper;
import io.stream.footer_entity.FooterEntityStream;
import io.stream.paging.PagingDataListSnapshotProvider;
import io.view.header.FooterEntity;
import io.view.header.FooterEntity.Error.ErrorAction;
import javax.inject.Inject;

public class FooterEntityGenerator implements AutoDisposeWorker {

  private final ErrorAction errorAction;
  private final PagingDataListSnapshotProvider pagingDataListSnapshotProvider;
  private final FooterEntityStream footerEntityStream;
  private final FooterLoadStateStreaming footerLoadStateStreaming;

  @Inject
  public FooterEntityGenerator(
      FooterEntity.Error.ErrorAction errorAction,
      PagingDataListSnapshotProvider pagingDataListSnapshotProvider,
      FooterEntityStream footerEntityStream,
      FooterLoadStateStreaming footerLoadStateStreaming) {
    this.errorAction = errorAction;
    this.pagingDataListSnapshotProvider = pagingDataListSnapshotProvider;
    this.footerEntityStream = footerEntityStream;
    this.footerLoadStateStreaming = footerLoadStateStreaming;
  }

  @Override
  public void attach(ScopeProvider scopeProvider) {
    footerLoadStateStreaming
        .streaming()
        .map(this::buildModel)
        .map(this::asEntity)
        .as(autoDisposable(scopeProvider))
        .subscribe(footerEntityStream::accept);
  }

  private Optional<FooterEntity> asEntity(PagingViewModel model) {
    return Optional.fromNullable(StateMapper.footerEntity(model, errorAction));
  }

  private PagingViewModel buildModel(LoadState state) {
    return PagingViewModel.create(state, pagingDataListSnapshotProvider.snapshot());
  }
}
