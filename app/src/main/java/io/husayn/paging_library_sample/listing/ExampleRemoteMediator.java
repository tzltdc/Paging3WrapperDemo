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
import java.io.IOException;
import timber.log.Timber;

class ExampleRemoteMediator extends RxRemoteMediator<Integer, Pokemon> {

  private final PokemonDataBase pokemonDataBase;
  private final PagingQuery query;
  private final ExampleBackendService networkService;
  private final PokemonDao pokemonDao;

  ExampleRemoteMediator(
      PagingQuery query,
      PokemonDataBase pokemonDataBase,
      ExampleBackendService networkService,
      PokemonDao pokemonDao) {
    this.pokemonDataBase = pokemonDataBase;
    this.query = query;
    this.networkService = networkService;
    this.pokemonDao = pokemonDao;
  }

  @NonNull
  @Override
  public Single<InitializeAction> initializeSingle() {
    return InitializeActionMapper.initializeSingle();
  }

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
    PagingRequest pagingRequest = defaultPagingRequest(query);
    return execute(loadType, pagingRequest);
  }

  private PagingRequest defaultPagingRequest(PagingQuery query) {
    return new PagingRequest(0, query, PagingQueryConfig.DEFAULT_QUERY_CONFIG);
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
      return execute(loadType, nextPagingRequest(query, lastItem));
    }
  }

  private Single<MediatorResult> execute(LoadType loadType, PagingRequest pagingRequest) {
    return networkService
        .searchPokemons(pagingRequest)
        .map(response -> success(loadType, new PagingAction(pagingRequest, response)))
        .onErrorResumeNext(this::error);
  }

  private PagingRequest nextPagingRequest(PagingQuery query, Pokemon lastItem) {
    return new PagingRequest(
        OffsetHelper.offset(lastItem, query), query, PagingQueryConfig.DEFAULT_QUERY_CONFIG);
  }

  private Single<MediatorResult> error(Throwable e) {
    if (e instanceof IOException) {
      return Single.just(new MediatorResult.Error(e));
    } else {
      return Single.error(e);
    }
  }

  private MediatorResult success(LoadType loadType, PagingAction action) {
    pokemonDataBase.runInTransaction(() -> flushDbData(loadType, action.response));
    return new Success(endOfPaging(action));
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

  private boolean endOfPaging(PagingAction action) {
    return EndOfPagingMapper.endOfPaging(action);
  }
}
