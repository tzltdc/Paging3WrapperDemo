package paging.wrapper.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava2.RxPagingSource;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.Single;
import paging.wrapper.mapper.PagingRequestMapper;
import paging.wrapper.model.data.PageActionResult;
import paging.wrapper.model.data.PagingQueryContext;
import paging.wrapper.model.data.PagingRemoteRequestConfig;
import paging.wrapper.model.data.Pokemon;
import timber.log.Timber;

class RemotePokenmonPagingSource extends RxPagingSource<Integer, Pokemon> {

  private final PagingQueryContext query;
  private final PokemonRemoteSource pokemonRemoteSource;
  private final PagingRequestMapper pagingRequestMapper;

  @AssistedInject
  RemotePokenmonPagingSource(
      @Assisted PagingQueryContext query,
      PokemonRemoteSource pokemonRemoteSource,
      PagingRequestMapper pagingRequestMapper) {
    this.query = query;
    this.pokemonRemoteSource = pokemonRemoteSource;
    this.pagingRequestMapper = pagingRequestMapper;
  }

  @NonNull
  @Override
  public Single<LoadResult<Integer, Pokemon>> loadSingle(@NonNull LoadParams<Integer> params) {
    return pokemonRemoteSource
        .fetch(pagingRequestMapper.nextPagingRequest(loadedCount(params), query.param()))
        .map(this::remotePagingResponse)
        .map(this::asLoadResult)
        .onErrorReturn(LoadResult.Error::new);
  }

  private RemotePagingResponse remotePagingResponse(PageActionResult response) {
    return RemotePagingResponse.create(
        response.response(), nextTargetCount(response.request().offSet(), response));
  }

  @Nullable
  private static Integer nextTargetCount(int offSet, PageActionResult response) {
    return hasMore(response) ? next(offSet, response) : null;
  }

  private static int next(int currentOffset, PageActionResult response) {
    return currentOffset + response.response().list().size();
  }

  private static boolean hasMore(PageActionResult response) {
    return response.response().list().size()
        >= PagingRemoteRequestConfig.DEFAULT_QUERY_CONFIG.countPerPage();
  }

  private Integer loadedCount(LoadParams<Integer> params) {
    Integer targetLoadCount = params.getKey();
    if (targetLoadCount == null) {
      targetLoadCount = 0;
      Timber.i("default loaded count 0 applied:%s", targetLoadCount);
    } else {
      Timber.i("dynamic loaded count applied:%s", targetLoadCount);
    }
    return targetLoadCount;
  }

  private LoadResult<Integer, Pokemon> asLoadResult(RemotePagingResponse remotePagingResponse) {
    return new LoadResult.Page<>(
        remotePagingResponse.response().list(),
        null,
        remotePagingResponse.nextTargetCount(),
        LoadResult.Page.COUNT_UNDEFINED,
        LoadResult.Page.COUNT_UNDEFINED);
  }

  @Nullable
  @Override
  public Integer getRefreshKey(@NonNull PagingState<Integer, Pokemon> state) {
    return null;
  }
}
