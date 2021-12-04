package paging.wrapper.data;

import static paging.wrapper.data.NextTargetCountMapper.nextTargetCount;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava2.RxPagingSource;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.Single;
import paging.wrapper.model.data.PageActionResult;
import paging.wrapper.model.data.PagingQueryContext;
import paging.wrapper.model.data.Pokemon;

class RemotePokenmonPagingSource extends RxPagingSource<Integer, Pokemon> {

  private final PagingQueryContext query;
  private final PokemonRemoteSource pokemonRemoteSource;

  @AssistedInject
  RemotePokenmonPagingSource(
      @Assisted PagingQueryContext query, PokemonRemoteSource pokemonRemoteSource) {
    this.query = query;
    this.pokemonRemoteSource = pokemonRemoteSource;
  }

  @NonNull
  @Override
  public Single<LoadResult<Integer, Pokemon>> loadSingle(@NonNull LoadParams<Integer> params) {
    return pokemonRemoteSource
        .fetch(RemoteLoadRequestMapper.getPagingRequest(params, query))
        .map(this::remotePagingResponse)
        .map(RemoteLoadResultMapper::asLoadResult)
        .onErrorReturn(LoadResult.Error::new);
  }

  private RemotePagingResponse remotePagingResponse(PageActionResult response) {
    return RemotePagingResponse.create(response.response(), nextTargetCount(response));
  }

  @Nullable
  @Override
  public Integer getRefreshKey(@NonNull PagingState<Integer, Pokemon> state) {
    return null;
  }
}
