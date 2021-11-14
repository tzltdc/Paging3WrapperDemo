package io.husayn.paging_library_sample.listing;

import androidx.annotation.Nullable;
import androidx.paging.LoadState;
import androidx.paging.LoadState.Loading;
import io.view.header.FooterEntity;
import io.view.header.FooterEntity.Error.ErrorAction;
import io.view.header.FooterEntity.NoMore;
import io.view.header.HeaderEntity;
import io.view.header.HeaderEntity.Empty;
import io.view.header.HeaderEntity.Error;

public class StateMapper {

  @Nullable
  public static HeaderEntity headerEntity(PagingViewModel model, Error.ErrorAction errorAction) {
    LoadState headerState = model.loadState();
    if (headerState instanceof Loading) {
      return HeaderEntity.ofLoading(HeaderEntity.Loading.create("Initial Loading"));
    } else if (headerState instanceof LoadState.Error) {
      return HeaderEntity.ofError(HeaderEntity.Error.create("Initial Loading Error", errorAction));
    } else {
      return model.snapshot().isEmpty()
          ? HeaderEntity.ofEmpty(Empty.create("No data at all"))
          : null;
    }
  }

  @Nullable
  public static FooterEntity footerEntity(PagingViewModel model, ErrorAction errorAction) {
    LoadState footerState = model.loadState();
    if (footerState instanceof Loading) {
      return FooterEntity.ofLoading(FooterEntity.Loading.create("Loading more"));
    } else if (footerState instanceof LoadState.Error) {
      return FooterEntity.ofError(FooterEntity.Error.create("Loading more Error", errorAction));
    } else {
      return model.snapshot().size() > 2
          ? FooterEntity.ofNoMore(NoMore.create("No more data"))
          : null;
    }
  }
}
