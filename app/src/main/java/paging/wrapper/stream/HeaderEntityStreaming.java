package paging.wrapper.stream;

import androidx.paging.LoadState;
import com.google.common.base.Optional;
import io.reactivex.Observable;
import javax.inject.Inject;
import paging.wrapper.contract.PagingDataListSnapshotProvider;
import paging.wrapper.mapper.StateMapper;
import paging.wrapper.model.ui.HeaderEntity;
import paging.wrapper.model.ui.PagingViewModel;

public class HeaderEntityStreaming {

  private final PagingDataListSnapshotProvider pagingDataListSnapshotProvider;
  private final StateMapper stateMapper;
  private final LoadStateStreaming loadStateStreaming;

  @Inject
  public HeaderEntityStreaming(
      PagingDataListSnapshotProvider pagingDataListSnapshotProvider,
      StateMapper stateMapper,
      LoadStateStreaming loadStateStreaming) {
    this.pagingDataListSnapshotProvider = pagingDataListSnapshotProvider;
    this.stateMapper = stateMapper;
    this.loadStateStreaming = loadStateStreaming;
  }

  public Observable<Optional<HeaderEntity>> streaming() {
    return loadStateStreaming.header().map(this::buildModel).map(this::asEntity);
  }

  private Optional<HeaderEntity> asEntity(PagingViewModel model) {
    return Optional.fromNullable(stateMapper.headerEntity(model));
  }

  private PagingViewModel buildModel(LoadState headerState) {
    return PagingViewModel.create(headerState, pagingDataListSnapshotProvider.snapshotItemList());
  }
}
