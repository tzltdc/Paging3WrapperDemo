package paging.wrapper.stream;

import androidx.pagingx.LoadState;
import com.google.common.base.Optional;
import io.reactivex.Observable;
import javax.inject.Inject;
import paging.wrapper.contract.PagingDataListSnapshotProvider;
import paging.wrapper.mapper.StateMapper;
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
    return PagingViewModel.create(state, pagingDataListSnapshotProvider.snapshotItemList());
  }
}
