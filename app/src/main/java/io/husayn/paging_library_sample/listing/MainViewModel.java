package io.husayn.paging_library_sample.listing;

import androidx.lifecycle.ViewModel;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingSource;
import androidx.paging.rxjava2.PagingRx;
import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.data.PokemonDao;
import io.reactivex.Observable;
import javax.inject.Inject;

public class MainViewModel extends ViewModel {

  private static final int INITIAL_LOAD_KEY = 0;

  private final PagingConfig androidPagingConfig;
  private final RxRemoteMediatorFactory rxRemoteMediatorFactory;
  private final QueryStreaming queryStreaming;
  private final PokemonDao pokemonDao;

  @Inject
  public MainViewModel(
      PagingConfig androidPagingConfig,
      RxRemoteMediatorFactory rxRemoteMediatorFactory,
      QueryStreaming queryStreaming,
      PokemonDao pokemonDao) {
    this.androidPagingConfig = androidPagingConfig;
    this.rxRemoteMediatorFactory = rxRemoteMediatorFactory;
    this.queryStreaming = queryStreaming;
    this.pokemonDao = pokemonDao;
  }

  public Observable<PagingData<Pokemon>> rxPagingData() {
    return queryStreaming.streaming().map(this::pager).switchMap(PagingRx::getObservable);
  }

  private Pager<Integer, Pokemon> pager(PagingQuery pagingQuery) {
    return new Pager<>(
        androidPagingConfig,
        INITIAL_LOAD_KEY,
        rxRemoteMediatorFactory.create(pagingQuery),
        () -> localPagingSource(this.pokemonDao, pagingQuery));
  }

  private PagingSource<Integer, Pokemon> localPagingSource(
      PokemonDao pokemonDao, PagingQuery query) {
    String name = query.searchKey();
    return name == null ? pokemonDao.allByAsc() : pokemonDao.queryBy(name);
  }
}
