package io.stream.load_state.footer;

import androidx.paging.LoadState;
import com.google.common.base.Optional;
import io.husayn.paging_library_sample.listing.PagingViewModel;
import io.husayn.paging_library_sample.listing.StateMapper;
import io.reactivex.Observable;
import io.stream.paging.PagingDataListSnapshotProvider;
import io.view.header.HeaderEntity;
import io.view.header.HeaderErrorAction;
import javax.inject.Inject;

public class HeaderEntityStreaming {

  private final HeaderErrorAction errorAction;
  private final PagingDataListSnapshotProvider pagingDataListSnapshotProvider;
  private final LoadStateStreaming loadStateStreaming;

  @Inject
  public HeaderEntityStreaming(
      HeaderErrorAction errorAction,
      PagingDataListSnapshotProvider pagingDataListSnapshotProvider,
      LoadStateStreaming loadStateStreaming) {
    this.errorAction = errorAction;
    this.pagingDataListSnapshotProvider = pagingDataListSnapshotProvider;
    this.loadStateStreaming = loadStateStreaming;
  }

  public Observable<Optional<HeaderEntity>> streaming() {
    return loadStateStreaming.header().map(this::buildModel).map(this::asEntity);
  }

  private Optional<HeaderEntity> asEntity(PagingViewModel model) {
    return Optional.fromNullable(StateMapper.headerEntity(model, errorAction));
  }

  private PagingViewModel buildModel(LoadState headerState) {
    return PagingViewModel.create(headerState, pagingDataListSnapshotProvider.snapshot());
  }
}
