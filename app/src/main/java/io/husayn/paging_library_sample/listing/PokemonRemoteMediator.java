package io.husayn.paging_library_sample.listing;

import androidx.annotation.NonNull;
import androidx.paging.LoadType;
import androidx.paging.PagingState;
import androidx.paging.rxjava2.RxRemoteMediator;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.husayn.paging_library_sample.data.Pokemon;
import io.reactivex.Single;
import io.thread.WorkerScheduler;
import timber.log.Timber;

class PokemonRemoteMediator extends RxRemoteMediator<Integer, Pokemon> {

  private final PagingQueryParam query;
  private final WorkerScheduler workerScheduler;
  private final PokemonInitialLoadSource pokemonInitialLoadSource;
  private final PokemonLoadMoreSource pokemonLoadMoreSource;

  @AssistedInject
  PokemonRemoteMediator(
      @Assisted PagingQueryParam query,
      WorkerScheduler workerScheduler,
      PokemonInitialLoadSource pokemonInitialLoadSource,
      PokemonLoadMoreSource pokemonLoadMoreSource) {
    this.query = query;
    this.workerScheduler = workerScheduler;
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
    switch (loadType) {
      case REFRESH:
        return refresh().subscribeOn(workerScheduler.get());
      case APPEND:
        return append().subscribeOn(workerScheduler.get());
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
    Timber.w("tonny Prepend LoadType ignored for query :%s", query);
    return Single.just(new MediatorResult.Success(true));
  }

  /**
   * The network load method takes an optional after=<Pokemon.id> parameter. For every page after
   * the first, pass the last Pokemon ID to let it continue from where it left off. For REFRESH,
   * pass null to load the first page.
   */
  private Single<MediatorResult> refresh() {
    return pokemonInitialLoadSource.load(query);
  }

  /**
   * You must explicitly check if the last item is null when appending, since passing null to
   * networkService is only valid for initial load. If lastItem is null it means no items were
   * loaded after the initial ø REFRESH and there are no more items to load.
   */
  private Single<MediatorResult> append() {
    return pokemonLoadMoreSource.loadingMore(query);
  }
}
