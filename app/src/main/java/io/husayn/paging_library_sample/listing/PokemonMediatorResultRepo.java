package io.husayn.paging_library_sample.listing;

import androidx.paging.RemoteMediator.MediatorResult;
import androidx.paging.RemoteMediator.MediatorResult.Success;
import io.reactivex.Single;
import javax.inject.Inject;
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
    return pokemonRemoteSource
        .fetch(pagingRequest)
        .map(this::success)
        .onErrorResumeNext(this::error);
  }

  private Single<MediatorResult> error(Throwable e) {
    Timber.e(e, "PokemonMediatorResultRepo");
    return Single.just(new MediatorResult.Error(e));
  }

  private MediatorResult success(PageActionResult result) {
    // FIXME: Resolve the side effects here.
    pokemonRepo.flushDbData(result);
    return new Success(endOfPaging(result));
  }

  private boolean endOfPaging(PageActionResult data) {
    return EndOfPagingMapper.endOfPaging(data);
  }
}
