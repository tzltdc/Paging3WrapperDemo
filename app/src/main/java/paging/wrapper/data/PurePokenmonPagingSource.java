package paging.wrapper.data;

import static paging.wrapper.data.NextTargetCountMapper.nextTargetCount;

import androidx.paging.PagingSource.LoadResult;
import androidx.paging.PagingSource.LoadResult.Error;
import io.reactivex.Single;
import javax.inject.Inject;
import paging.wrapper.model.data.PageActionResult;
import paging.wrapper.model.data.PagingRequest;
import paging.wrapper.model.data.Pokemon;

class PurePokenmonPagingSource {

  private final PokemonRemoteSource pokemonRemoteSource;

  @Inject
  PurePokenmonPagingSource(PokemonRemoteSource pokemonRemoteSource) {
    this.pokemonRemoteSource = pokemonRemoteSource;
  }

  public Single<LoadResult<Integer, Pokemon>> execute(PagingRequest pagingRequest) {
    return requestSignal(pagingRequest)
        .flatMap(pokemonRemoteSource::fetch)
        .map(this::remotePagingResponse)
        .map(RemoteLoadResultMapper::asLoadResult)
        .onErrorReturn(Error::new);
  }

  private Single<PagingRequest> requestSignal(PagingRequest pagingRequest) {
    return Single.just(pagingRequest);
  }

  private RemotePagingResponse remotePagingResponse(PageActionResult response) {
    return RemotePagingResponse.create(response.response(), nextTargetCount(response));
  }
}
