package paging.wrapper.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingSource.LoadResult.Error;
import androidx.paging.PagingState;
import androidx.paging.rxjava2.RxPagingSource;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.Single;
import java.util.concurrent.TimeUnit;
import paging.wrapper.demo.ui.query.FilterOptionProvider;
import paging.wrapper.di.thread.AppScheduler;
import paging.wrapper.model.data.PagingQueryContext;
import paging.wrapper.model.data.PagingRequest;
import paging.wrapper.model.data.Pokemon;
import timber.log.Timber;

class RemotePokenmonPagingSource extends RxPagingSource<Integer, Pokemon> {

  private final PagingQueryContext query;
  private final AppScheduler appScheduler;
  private final PurePokenmonPagingSource purePokenmonPagingSource;
  private int retryCount = 0;
  private int retryMoreCount = 0;

  @AssistedInject
  RemotePokenmonPagingSource(
      @Assisted PagingQueryContext query,
      AppScheduler appScheduler,
      PurePokenmonPagingSource purePokenmonPagingSource) {
    this.query = query;
    this.appScheduler = appScheduler;
    this.purePokenmonPagingSource = purePokenmonPagingSource;
  }

  @NonNull
  @Override
  public Single<LoadResult<Integer, Pokemon>> loadSingle(@NonNull LoadParams<Integer> params) {
    Timber.i("[remote]:loadSingle:%s", ToStringUtil.toString(params));
    return simulateInitialError(query.description())
        ? simulateError("[remote]:dataSource simulated initial error")
        : load(params);
  }

  private boolean showLoadMoreError(String desc) {
    return FilterOptionProvider.LOAD_MORE_ERROR.equals(desc) && ++retryMoreCount % 2 == 0;
  }

  private Single<LoadResult<Integer, Pokemon>> simulateError(String errorMessage) {
    Timber.i("[remote]:simulate error for query:%s", query);
    return Single.timer(1000, TimeUnit.MILLISECONDS, appScheduler.worker())
        .map(ignored -> new Error<>(new RuntimeException(errorMessage)));
  }

  private Single<LoadResult<Integer, Pokemon>> load(@NonNull LoadParams<Integer> params) {
    return showLoadMoreError(query.description())
        ? simulateError("[remote]:dataSource load_more error")
        : execute(params);
  }

  private Single<LoadResult<Integer, Pokemon>> execute(@NonNull LoadParams<Integer> params) {
    PagingRequest pagingRequest = RemoteLoadRequestMapper.getPagingRequest(params, query);
    return purePokenmonPagingSource.execute(pagingRequest);
  }

  private boolean simulateInitialError(String description) {
    return FilterOptionProvider.INITIAL_LOAD_ERROR.equals(description) && retryCount++ % 2 == 0;
  }

  @Nullable
  @Override
  public Integer getRefreshKey(@NonNull PagingState<Integer, Pokemon> state) {
    Timber.i("[remote]:getRefreshKey:%s", state);
    return null;
  }
}
