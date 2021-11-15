package io.stream.load_state.footer;

import androidx.paging.LoadState;
import com.google.common.base.Optional;
import io.husayn.paging_library_sample.listing.PagingViewModel;
import io.husayn.paging_library_sample.listing.StateMapper;
import io.reactivex.Observable;
import io.stream.paging.PagingDataListSnapshotProvider;
import io.view.header.FooterEntity;
import io.view.header.FooterEntity.Error.ErrorAction;
import javax.inject.Inject;

public class RawFooterEntityStreaming {

  private final ErrorAction errorAction;
  private final PagingDataListSnapshotProvider pagingDataListSnapshotProvider;
  private final FooterLoadStateStreaming footerLoadStateStreaming;

  @Inject
  public RawFooterEntityStreaming(
      ErrorAction errorAction,
      PagingDataListSnapshotProvider pagingDataListSnapshotProvider,
      FooterLoadStateStreaming footerLoadStateStreaming) {
    this.errorAction = errorAction;
    this.pagingDataListSnapshotProvider = pagingDataListSnapshotProvider;
    this.footerLoadStateStreaming = footerLoadStateStreaming;
  }

  public Observable<Optional<FooterEntity>> streaming() {
    return footerLoadStateStreaming.streaming().map(this::buildModel).map(this::asEntity);
  }

  private Optional<FooterEntity> asEntity(PagingViewModel model) {
    return Optional.fromNullable(StateMapper.footerEntity(model, errorAction));
  }

  private PagingViewModel buildModel(LoadState state) {
    return PagingViewModel.create(state, pagingDataListSnapshotProvider.snapshot());
  }
}
