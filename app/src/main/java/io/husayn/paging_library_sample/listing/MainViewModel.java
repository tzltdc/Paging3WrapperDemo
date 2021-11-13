package io.husayn.paging_library_sample.listing;

import androidx.lifecycle.ViewModel;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingSource;
import androidx.paging.RemoteMediator;
import androidx.paging.rxjava2.PagingRx;
import io.husayn.paging_library_sample.PokemonApplication;
import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.data.PokemonDao;
import io.husayn.paging_library_sample.data.PokemonDataBase;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import javax.inject.Inject;

public class MainViewModel extends ViewModel {

  private static final int INITIAL_LOAD_KEY = 0;
  private static final int PAGE_SIZE = 20;
  private static final int PREFETCH_DISTANCE = 5;
  private final BehaviorSubject<PagingQuery> query = BehaviorSubject.create();
  private final PokemonDao pokemonDao;

  @Inject
  public MainViewModel() {
    pokemonDao = PokemonDataBase.getInstance(PokemonApplication.getContext()).pokemonDao();
  }

  public Observable<PagingData<Pokemon>> rxPagingData() {
    return query
        .hide()
        .switchMap(orderByDesc -> PagingRx.getObservable(pager(pokemonDao, orderByDesc)));
  }

  private Pager<Integer, Pokemon> pager(PokemonDao pokemonDao, PagingQuery orderByDesc) {
    PagingConfig pagingConfig = new PagingConfig(PAGE_SIZE, PREFETCH_DISTANCE, true);
    return new Pager<>(
        pagingConfig,
        INITIAL_LOAD_KEY,
        remoteMediator(orderByDesc),
        () -> pagingSource(pokemonDao, orderByDesc));
  }

  private RemoteMediator<Integer, Pokemon> remoteMediator(PagingQuery orderByDesc) {
    return new ExampleRemoteMediator(
        orderByDesc, PokemonDataBase.getInstance(PokemonApplication.getContext()), pokemonDao);
  }

  private PagingSource<Integer, Pokemon> pagingSource(PokemonDao pokemonDao, PagingQuery query) {
    String name = query.searchKey();
    return name == null ? pokemonDao.allByAsc() : pokemonDao.queryBy(name);
  }

  public void postValue(PagingQuery orderByDesc) {
    query.onNext(orderByDesc);
  }
}
