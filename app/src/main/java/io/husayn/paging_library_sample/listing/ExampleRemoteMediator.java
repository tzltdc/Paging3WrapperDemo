package io.husayn.paging_library_sample.listing;

import androidx.annotation.NonNull;
import androidx.paging.LoadType;
import androidx.paging.PagingState;
import androidx.paging.rxjava2.RxRemoteMediator;
import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.data.PokemonDao;
import io.husayn.paging_library_sample.data.PokemonDataBase;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
    // The network load method takes an optional after=<Pokemon.id> parameter. For
    // every page after the first, pass the last Pokemon ID to let it continue from
    // where it left off. For REFRESH, pass null to load the first page.
    Integer loadKey = null;
    switch (loadType) {
      case REFRESH:
        break;
      case PREPEND:
        // In this example, you never need to prepend, since REFRESH will always
        // load the first page in the list. Immediately return, reporting end of
        // pagination.
        return Single.just(new MediatorResult.Success(true));
      case APPEND:
        Pokemon lastItem = state.lastItemOrNull();

        // You must explicitly check if the last item is null when appending,
        // since passing null to networkService is only valid for initial load.
        // If lastItem is null it means no items were loaded after the initial
        // REFRESH and there are no more items to load.
        if (lastItem == null) {
          return Single.just(new MediatorResult.Success(true));
        }

        loadKey = lastItem.id;
        break;
    }

    return networkService
        .searchPokemons(query, loadKey)
        .subscribeOn(Schedulers.io())
        .map(
            (Function<SearchPokemonResponse, MediatorResult>)
                response -> {
                  pokemonDataBase.runInTransaction(
                      () -> {
                        if (loadType == LoadType.REFRESH) {
                          pokemonDao.deleteByQuery(String.valueOf(query));
                        }

                        // Insert new Pokemons into database, which invalidates the current
                        // PagingData, allowing Paging to present the updates in the DB.
                        pokemonDao.insertAll(response.getPokemons());
                      });

                  return new MediatorResult.Success(response.getNextKey() == null);
                })
        .onErrorResumeNext(
            e -> {
              if (e instanceof IOException) {
                return Single.just(new MediatorResult.Error(e));
              }
              return Single.error(e);
            });
  }
}
