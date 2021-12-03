package paging.wrapper.data;

import androidx.annotation.NonNull;
import androidx.paging.LoadType;
import androidx.paging.PagingState;
import androidx.paging.rxjava2.RxRemoteMediator;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.Single;
import paging.wrapper.di.thread.AppScheduler;
import paging.wrapper.model.data.PagingQueryContext;
import paging.wrapper.model.data.Pokemon;
import timber.log.Timber;

public class PokemonRemoteMediator extends RxRemoteMediator<Integer, Pokemon> {

  private final PagingQueryContext query;
  private final AppScheduler appScheduler;
  private final PokemonInitialLoadSource pokemonInitialLoadSource;
  private final PokemonLoadMoreSource pokemonLoadMoreSource;

  @AssistedInject
  PokemonRemoteMediator(
      @Assisted PagingQueryContext query,
      AppScheduler appScheduler,
      PokemonInitialLoadSource pokemonInitialLoadSource,
      PokemonLoadMoreSource pokemonLoadMoreSource) {
    this.query = query;
    this.appScheduler = appScheduler;
    this.pokemonInitialLoadSource = pokemonInitialLoadSource;
    this.pokemonLoadMoreSource = pokemonLoadMoreSource;
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
    Timber.i(
        "[ttt]:PagingMediator action ignored PagingState param:[anchorPosition:%s], [lastItemOrNull:%s]",
        state.getAnchorPosition(), state.lastItemOrNull());
    Timber.i(
        "[ttt]:PagingMediator action triggered with loadSingle function with param: [loadType:%s] for [query:%s]",
        loadType, query);
    switch (loadType) {
      case REFRESH:
        return refresh().subscribeOn(appScheduler.worker());
      case APPEND:
        return append().subscribeOn(appScheduler.worker());
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
    Timber.w(
        "[ttt]:PagingMediator ignored PREPEND LoadType for query :%s and return Success directly",
        query);
    return Single.just(new MediatorResult.Success(true));
  }

  /**
   * The network load method takes an optional after=<Pokemon.id> parameter. For every page after
   * the first, pass the last Pokemon ID to let it continue from where it left off. For REFRESH,
   * pass null to load the first page.
   */
  private Single<MediatorResult> refresh() {
    Timber.i("[ttt]:PagingMediator is to conduct its first load for query:%s", query);
    return pokemonInitialLoadSource.load(query);
  }

  /**
   * You must explicitly check if the last item is null when appending, since passing null to
   * networkService is only valid for initial load. If lastItem is null it means no items were
   * loaded after the initial ø REFRESH and there are no more items to load.
   */
  private Single<MediatorResult> append() {
    Timber.i("[ttt]:PagingMediator is to load more data for query:%s", query);
    return pokemonLoadMoreSource.loadingMore(query);
  }
}
