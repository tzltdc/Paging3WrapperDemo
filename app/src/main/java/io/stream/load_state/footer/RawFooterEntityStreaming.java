package io.stream.load_state.footer;

import androidx.paging.LoadState;
import com.google.common.base.Optional;
import io.husayn.paging_library_sample.listing.StateMapper;
import io.reactivex.Observable;
import io.stream.paging.PagingDataListSnapshotProvider;
import javax.inject.Inject;
import paging.wrapper.model.ui.FooterEntity;
import paging.wrapper.model.ui.PagingViewModel;

public class RawFooterEntityStreaming {

  private final PagingDataListSnapshotProvider pagingDataListSnapshotProvider;
  private final LoadStateStreaming loadStateStreaming;
  private final StateMapper stateMapper;

  @Inject
  public RawFooterEntityStreaming(
      PagingDataListSnapshotProvider pagingDataListSnapshotProvider,
      LoadStateStreaming loadStateStreaming,
      StateMapper stateMapper) {
    this.pagingDataListSnapshotProvider = pagingDataListSnapshotProvider;
    this.loadStateStreaming = loadStateStreaming;
    this.stateMapper = stateMapper;
  }

  public Observable<Optional<FooterEntity>> streaming() {
    return loadStateStreaming.footer().map(this::buildModel).map(this::asEntity);
  }

  private Optional<FooterEntity> asEntity(PagingViewModel model) {
    return Optional.fromNullable(stateMapper.footerEntity(model));
  }

  private PagingViewModel buildModel(LoadState state) {
    return PagingViewModel.create(state, pagingDataListSnapshotProvider.snapshot());
  }
}
