package io.stream.load_state.footer;

import androidx.paging.LoadState;
import com.google.common.base.Optional;
import io.husayn.paging_library_sample.listing.PagingViewModel;
import io.husayn.paging_library_sample.listing.StateMapper;
import io.reactivex.Observable;
import io.stream.paging.PagingDataListSnapshotProvider;
import io.view.header.FooterEntity;
import javax.inject.Inject;

public class RawFooterEntityStreaming {

  private final PagingDataListSnapshotProvider pagingDataListSnapshotProvider;
  private final LoadStateStreaming loadStateStreaming;

  @Inject
  public RawFooterEntityStreaming(
      PagingDataListSnapshotProvider pagingDataListSnapshotProvider,
      LoadStateStreaming loadStateStreaming) {
    this.pagingDataListSnapshotProvider = pagingDataListSnapshotProvider;
    this.loadStateStreaming = loadStateStreaming;
  }

  public Observable<Optional<FooterEntity>> streaming() {
    return loadStateStreaming.footer().map(this::buildModel).map(this::asEntity);
  }

  private Optional<FooterEntity> asEntity(PagingViewModel model) {
    return Optional.fromNullable(StateMapper.footerEntity(model));
  }

  private PagingViewModel buildModel(LoadState state) {
    return PagingViewModel.create(state, pagingDataListSnapshotProvider.snapshot());
  }
}
