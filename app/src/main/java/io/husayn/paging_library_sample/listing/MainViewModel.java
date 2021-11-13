package io.husayn.paging_library_sample.listing;

import androidx.lifecycle.ViewModel;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingSource;
import androidx.paging.RemoteMediator;
import androidx.paging.rxjava2.PagingRx;
import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.data.PokemonDao;
import io.reactivex.Observable;
import javax.inject.Inject;

public class MainViewModel extends ViewModel {

  private static final int INITIAL_LOAD_KEY = 0;
  private static final int PAGE_SIZE = 20;
  private static final int PREFETCH_DISTANCE = 5;
  private final RxRemoteMediatorFactory rxRemoteMediatorFactory;
  private final QueryStreaming queryStreaming;
  private final PokemonDao pokemonDao;

  @Inject
  public MainViewModel(
      RxRemoteMediatorFactory rxRemoteMediatorFactory,
      QueryStreaming queryStreaming,
      PokemonDao pokemonDao) {
    this.rxRemoteMediatorFactory = rxRemoteMediatorFactory;
    this.queryStreaming = queryStreaming;
    this.pokemonDao = pokemonDao;
  }

  public Observable<PagingData<Pokemon>> rxPagingData() {
    return queryStreaming
        .streaming()
        .map(pagingQuery -> pager(pagingQuery, rxRemoteMediatorFactory.create(pagingQuery)))
        .switchMap(PagingRx::getObservable);
  }

  private Pager<Integer, Pokemon> pager(
      PagingQuery pagingQuery, RemoteMediator<Integer, Pokemon> integerPokemonRemoteMediator) {
    PagingConfig pagingConfig = new PagingConfig(PAGE_SIZE, PREFETCH_DISTANCE, true);
    return new Pager<>(
        pagingConfig,
        INITIAL_LOAD_KEY,
        integerPokemonRemoteMediator,
        () -> pagingSource(this.pokemonDao, pagingQuery));
  }

  private PagingSource<Integer, Pokemon> pagingSource(PokemonDao pokemonDao, PagingQuery query) {
    String name = query.searchKey();
    return name == null ? pokemonDao.allByAsc() : pokemonDao.queryBy(name);
  }
}
