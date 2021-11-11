package io.husayn.paging_library_sample.listing;

import androidx.annotation.Nullable;
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

public class MainViewModel extends ViewModel {

  private static final int INITIAL_LOAD_KEY = 0;
  private static final int PAGE_SIZE = 20;
  private static final int PREFETCH_DISTANCE = 5;
  private final BehaviorSubject<Boolean> query = BehaviorSubject.create();
  private final PokemonDao pokemonDao;

  public MainViewModel() {
    pokemonDao = PokemonDataBase.getInstance(PokemonApplication.getContext()).pokemonDao();
  }

  public Observable<PagingData<Pokemon>> rxPagingData() {
    return query
        .hide()
        .switchMap(orderByDesc -> PagingRx.getObservable(pager(pokemonDao, orderByDesc)));
  }

  private Pager<Integer, Pokemon> pager(PokemonDao pokemonDao, Boolean orderByDesc) {
    PagingConfig pagingConfig = new PagingConfig(PAGE_SIZE, PREFETCH_DISTANCE, true);
    return new Pager<>(
        pagingConfig,
        INITIAL_LOAD_KEY,
        remoteMediator(),
        () -> pagingSource(pokemonDao, orderByDesc));
  }

  @Nullable
  private RemoteMediator<Integer, Pokemon> remoteMediator() {
    return null;
  }

  private PagingSource<Integer, Pokemon> pagingSource(PokemonDao pokemonDao, Boolean orderByDesc) {
    return orderByDesc ? pokemonDao.allByDesc() : pokemonDao.allByAsc();
  }

  public void postValue(boolean orderByDesc) {
    query.onNext(orderByDesc);
  }
}
