package io.husayn.paging_library_sample.listing;

import androidx.annotation.NonNull;
import androidx.paging.LoadType;
import androidx.paging.PagingState;
import androidx.paging.RemoteMediator.MediatorResult.Success;
import androidx.paging.rxjava2.RxRemoteMediator;
import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.data.PokemonDao;
import io.husayn.paging_library_sample.data.PokemonDataBase;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

class ExampleRemoteMediator extends RxRemoteMediator<Integer, Pokemon> {

  private final PokemonDataBase pokemonDataBase;
  private Boolean query;
  private ExampleBackendService networkService;
  private PokemonDao pokemonDao;

  ExampleRemoteMediator(
      boolean query,
      PokemonDataBase pokemonDataBase,
      ExampleBackendService networkService,
      PokemonDao pokemonDao) {
    this.pokemonDataBase = pokemonDataBase;
    query = query;
    networkService = networkService;
    this.pokemonDao = pokemonDao;
  }

  @NonNull
  @Override
  public Single<InitializeAction> initializeSingle() {
    long cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS);
    return getLastUpdatedSingle()
        .map(
            lastUpdatedMillis -> {
              if (System.currentTimeMillis() - lastUpdatedMillis >= cacheTimeout) {
                // Cached data is up-to-date, so there is no need to re-fetch
                // from the network.
                return InitializeAction.SKIP_INITIAL_REFRESH;
              } else {
                // Need to refresh cached data from network; returning
                // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
                // APPEND and PREPEND from running until REFRESH succeeds.
                return InitializeAction.LAUNCH_INITIAL_REFRESH;
              }
            });
  }

  private Single<Long> getLastUpdatedSingle() {
    throw new RuntimeException("");
    //    return pokemonDao.lastUpdatedSingle();
  }

  // Prepare to fix it
  @NonNull
  @Override
  public Single<MediatorResult> loadSingle(
      @NonNull LoadType loadType, @NonNull PagingState<Integer, Pokemon> state) {
    switch (loadType) {
      case REFRESH:
        return refresh(loadType);
      case APPEND:
        return append(loadType, state);
      case PREPEND:
      default:
        return ignorePrepend();
    }
  }

  /**
   * In this example, you never need to prepend, since REFRESH will always load the first page in
   * the list. Immediately return, reporting end of pagination.
   */
  private Single<MediatorResult> ignorePrepend() {
    Timber.w("Prepend LoadType ignored");
    return Single.just(new MediatorResult.Success(true));
  }

  /**
   * The network load method takes an optional after=<Pokemon.id> parameter. For every page after
   * the first, pass the last Pokemon ID to let it continue from where it left off. For REFRESH,
   * pass null to load the first page.
   */
  private Single<MediatorResult> refresh(LoadType loadType) {
    Timber.w("refresh :%s", loadType);
    return fetch(loadType, networkService.searchPokemons(query, null));
  }

  /**
   * You must explicitly check if the last item is null when appending, since passing null to
   * networkService is only valid for initial load. If lastItem is null it means no items were
   * loaded after the initial ø REFRESH and there are no more items to load.
   */
  private Single<MediatorResult> append(LoadType loadType, PagingState<Integer, Pokemon> state) {
    Timber.w("append :%s", loadType);
    Pokemon lastItem = state.lastItemOrNull();
    if (lastItem == null) {
      return Single.just(new MediatorResult.Success(true));
    } else {
      return fetch(loadType, networkService.searchPokemons(query, lastItem.id));
    }
  }

  private Single<MediatorResult> fetch(
      LoadType loadType, Single<SearchPokemonResponse> searchPokemonResponseSingle) {
    return searchPokemonResponseSingle
        .subscribeOn(Schedulers.io())
        .map(response -> success(loadType, response))
        .onErrorResumeNext(this::error);
  }

  private Single<MediatorResult> error(Throwable e) {
    if (e instanceof IOException) {
      return Single.just(new MediatorResult.Error(e));
    } else {
      return Single.error(e);
    }
  }

  private MediatorResult success(LoadType loadType, SearchPokemonResponse response) {
    pokemonDataBase.runInTransaction(() -> flushDbData(loadType, response));
    boolean endOfPaginationReached = endOfPaging(response);
    return new Success(endOfPaginationReached);
  }

  /**
   * Insert new Pokemons into database, which invalidates the current PagingData, allowing Paging to
   * present the updates in the DB.
   */
  private void flushDbData(LoadType loadType, SearchPokemonResponse response) {
    if (loadType == LoadType.REFRESH) {
      pokemonDao.deleteByQuery(String.valueOf(query));
    }
    pokemonDao.insertAll(response.getPokemons());
  }

  private boolean endOfPaging(SearchPokemonResponse response) {
    return response.getNextKey() == null;
  }
}
