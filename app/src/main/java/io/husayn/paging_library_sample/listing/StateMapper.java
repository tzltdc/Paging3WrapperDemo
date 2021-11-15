package io.husayn.paging_library_sample.listing;

import androidx.annotation.Nullable;
import androidx.paging.LoadState;
import androidx.paging.LoadState.Loading;
import io.view.header.ErrorData;
import io.view.header.FooterEntity;
import io.view.header.FooterEntity.LoadingMore;
import io.view.header.FooterEntity.NoMore;
import io.view.header.HeaderEntity;
import io.view.header.HeaderEntity.Empty;
import javax.inject.Inject;

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
    LoadingUIConfig config = loadingUIConfig;
    LoadState headerState = model.loadState();
    if (headerState instanceof Loading) {
      return HeaderEntity.ofLoading(HeaderEntity.Loading.create(config.loadingHeader()));
    } else if (headerState instanceof LoadState.Error) {
      return HeaderEntity.ofError(
          ErrorData.create(config.loadingHeaderError(), config.loadingRetry()));
    } else {
      return model.snapshot().isEmpty()
          ? HeaderEntity.ofEmpty(Empty.create(config.emptyData()))
          : null;
    }
  }

  @Nullable
  public FooterEntity footerEntity(PagingViewModel model) {
    LoadingUIConfig config = loadingUIConfig;
    LoadState footerState = model.loadState();
    if (footerState instanceof Loading) {
      return FooterEntity.ofLoading(LoadingMore.create(config.loadingMore()));
    } else if (footerState instanceof LoadState.Error) {
      return FooterEntity.ofError(
          ErrorData.create(config.loadingMoreError(), config.loadingMoreRetry()));
    } else {
      return footerState.getEndOfPaginationReached() && hasEnoughData(model, config.miniDataSize())
          ? FooterEntity.ofNoMore(NoMore.create(config.noMoreData()))
          : emptyFooter();
    }
  }

  private static FooterEntity emptyFooter() {
    return null;
  }

  private static boolean hasEnoughData(PagingViewModel model, int miniDataSize) {
    return model.snapshot().size() >= miniDataSize;
  }
}
