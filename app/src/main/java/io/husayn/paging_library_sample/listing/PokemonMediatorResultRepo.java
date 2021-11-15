package io.husayn.paging_library_sample.listing;

import androidx.paging.RemoteMediator.MediatorResult;
import androidx.paging.RemoteMediator.MediatorResult.Success;
import io.reactivex.Single;
import javax.inject.Inject;
import paging.wrapper.model.data.PageActionResult;
import paging.wrapper.model.data.PagingRequest;
import timber.log.Timber;

public class PokemonMediatorResultRepo {

  private final PokemonRepo pokemonRepo;
  private final PokemonRemoteSource pokemonRemoteSource;

  @Inject
  public PokemonMediatorResultRepo(
      PokemonRepo pokemonRepo, PokemonRemoteSource pokemonRemoteSource) {
    this.pokemonRepo = pokemonRepo;
    this.pokemonRemoteSource = pokemonRemoteSource;
  }

  public Single<MediatorResult> request(PagingRequest pagingRequest) {
    Timber.i("[ttt]:MediatorResult Repo is receiving PagingRequest:%s", pagingRequest);
    return pokemonRemoteSource
        .fetch(pagingRequest)
        .doOnSuccess(this::persistData)
        .map(this::asSuccess)
        .onErrorResumeNext(this::error);
  }

  // FIXME: Resolve the side effects here.
  private void persistData(PageActionResult result) {
    Timber.i(
        "[ttt]:Writing remote data source for request:%s with response :%s into database.",
        result.request(), result.response().list());
    pokemonRepo.flushDbData(result);
    Timber.i("[ttt]:Finished writing remote data source");
  }

  private Single<MediatorResult> error(Throwable e) {
    Timber.e(e, "[ttt]:error in fetching remote data");
    return Single.just(new MediatorResult.Error(e));
  }

  private MediatorResult asSuccess(PageActionResult result) {
    boolean endOfPaginationReached = endOfPaging(result);
    Timber.i(
        "[ttt]:Returning page request:[%s] with response :%s with endOfPaginationReached:%s",
        result.request(), result.response().list(), endOfPaginationReached);
    return new Success(endOfPaginationReached);
  }

  private boolean endOfPaging(PageActionResult data) {
    return EndOfPagingMapper.endOfPaging(data);
  }
}
