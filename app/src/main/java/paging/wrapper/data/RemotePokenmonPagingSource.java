package paging.wrapper.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava2.RxPagingSource;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.Single;
import java.util.List;
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

    int loadedCount = loadedCount(params);
    return pokemonRemoteSource
        .fetch(pagingRequestMapper.nextPagingRequest(loadedCount, query.param()))
        .map(response -> asResult(response, mutate(loadedCount, response)))
        .onErrorReturn(LoadResult.Error::new);
  }

  @Nullable
  private Integer mutate(int count, PageActionResult response) {
    return hasMore(response) ? next(count, response) : null;
  }

  private int next(int current, PageActionResult response) {
    return current + response.response().list().size();
  }

  private boolean hasMore(PageActionResult response) {
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

  private LoadResult<Integer, Pokemon> asResult(
      PageActionResult response, @Nullable Integer nextKey) {
    return getIntegerPokemonLoadResult(response, nextKey);
  }

  private LoadResult<Integer, Pokemon> getIntegerPokemonLoadResult(
      PageActionResult response, @Nullable Integer nextTargetCount) {
    List<Pokemon> list = response.response().list();
    Integer prevKey = null;
    return new LoadResult.Page<>(
        list,
        prevKey, // Only paging forward.
        nextTargetCount,
        LoadResult.Page.COUNT_UNDEFINED,
        LoadResult.Page.COUNT_UNDEFINED);
  }

  @Nullable
  @Override
  public Integer getRefreshKey(@NonNull PagingState<Integer, Pokemon> state) {
    return null;
  }
}
