package io.husayn.paging_library_sample.listing;

import androidx.paging.RemoteMediator.MediatorResult;
import androidx.paging.RemoteMediator.MediatorResult.Success;
import io.husayn.paging_library_sample.data.Pokemon;
import io.reactivex.Single;
import javax.inject.Inject;
import timber.log.Timber;

public class PokemonLoadMoreSource {

  private final PokemonRepo pokemonRepo;
  private final PokemonMediatorResultRepo pokemonMediatorResultRepo;

  @Inject
  public PokemonLoadMoreSource(
      PokemonRepo pokemonRepo, PokemonMediatorResultRepo pokemonMediatorResultRepo) {
    this.pokemonRepo = pokemonRepo;
    this.pokemonMediatorResultRepo = pokemonMediatorResultRepo;
  }

  /**
   * You must explicitly check if the last item is null when appending, since passing null to
   * networkService is only valid for initial load. If lastItem is null it means no items were
   * loaded after the initial ø REFRESH and there are no more items to load.
   */
  public Single<MediatorResult> loadingMore(PagingQueryParam query) {
    return showLoadMoreError(query) ? simulateError() : Single.defer(() -> execute(query));
  }

  private boolean showLoadMoreError(PagingQueryParam query) {
    return FilterOptionProvider.LOAD_MORE_ERROR.equals(query.searchKey());
  }

  private Single<MediatorResult> simulateError() {
    return Single.just(new MediatorResult.Error(new RuntimeException("loading more happened")));
  }

  private Single<MediatorResult> execute(PagingQueryParam query) {
    Pokemon lastItem = pokemonRepo.lastItemOrNull(query);
    Timber.i("loading more with query:%s, last_item:%s", query, lastItem);
    if (lastItem == null) {
      return Single.just(new Success(true));
    } else {
      PagingRequest pagingRequest = PagingRequestMapper.nextPagingRequest(query, lastItem);
      return pokemonMediatorResultRepo.request(pagingRequest);
    }
  }
}
