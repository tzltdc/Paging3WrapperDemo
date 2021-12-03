package paging.wrapper.mapper;

import androidx.annotation.Nullable;
import androidx.pagingx.LoadState;
import javax.inject.Inject;
import paging.wrapper.model.ui.ErrorData;
import paging.wrapper.model.ui.FooterEntity;
import paging.wrapper.model.ui.FooterEntity.LoadingMore;
import paging.wrapper.model.ui.FooterEntity.NoMore;
import paging.wrapper.model.ui.HeaderEntity;
import paging.wrapper.model.ui.HeaderEntity.Empty;
import paging.wrapper.model.ui.LoadingUIConfig;
import paging.wrapper.model.ui.PagingViewModel;
import timber.log.Timber;

public class StateMapper {

  private final LoadingUIConfig loadingUIConfig;

  @Inject
  public StateMapper() {
    this.loadingUIConfig = defaultLoadingUIConfig();
  }

  public static LoadingUIConfig defaultLoadingUIConfig() {
    return LoadingUIConfig.builder()
        .loadingHeader("Initial Loading")
        .loadingHeaderError("Initial Loading Error")
        .emptyData("No data at all")
        .loadingRetry("Reload")
        .loadingMore("Loading more")
        .loadingMoreError("Loading more error")
        .loadingMoreRetry("Retry")
        .noMoreData("No more data")
        .miniDataSize(2)
        .build();
  }

  @Nullable
  public HeaderEntity headerEntity(PagingViewModel model) {
    Timber.i("headerEntity source model:%s", model);
    LoadState headerState = model.loadState();
    switch (headerState.status()) {
      case NOT_LOADING:
        return model.snapshot().isEmpty()
            ? HeaderEntity.ofEmpty(Empty.create(loadingUIConfig.emptyData()))
            : null;
      case LOADING:
        return HeaderEntity.ofLoading(HeaderEntity.Loading.create(loadingUIConfig.loadingHeader()));
      case ERROR:
        return HeaderEntity.ofError(
            ErrorData.create(loadingUIConfig.loadingHeaderError(), loadingUIConfig.loadingRetry()));
      default:
        throw new RuntimeException("Invalid LoadState Status:" + headerState.status());
    }
  }

  @Nullable
  public FooterEntity footerEntity(PagingViewModel model) {
    LoadState footerState = model.loadState();
    switch (footerState.status()) {
      case NOT_LOADING:
        return footerState.notLoading().endOfPaginationReached()
                && hasEnoughData(model, loadingUIConfig.miniDataSize())
            ? FooterEntity.ofNoMore(NoMore.create(loadingUIConfig.noMoreData()))
            : emptyFooter();
      case LOADING:
        return FooterEntity.ofLoading(LoadingMore.create(loadingUIConfig.loadingMore()));

      case ERROR:
        return FooterEntity.ofError(
            ErrorData.create(
                loadingUIConfig.loadingMoreError(), loadingUIConfig.loadingMoreRetry()));
      default:
        throw new RuntimeException("Invalid LoadState Status:" + footerState.status());
    }
  }

  private static FooterEntity emptyFooter() {
    return null;
  }

  private static boolean hasEnoughData(PagingViewModel model, int miniDataSize) {
    return model.snapshot().size() >= miniDataSize;
  }
}
