package io.husayn.paging_library_sample.listing;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;
import androidx.paging.PagingSource;
import androidx.paging.RemoteMediator;
import io.husayn.paging_library_sample.PokemonApplication;
import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.data.PokemonDao;
import io.husayn.paging_library_sample.data.PokemonDataBase;

public class MainViewModel extends ViewModel {

  private static final int INITIAL_LOAD_KEY = 0;
  private static final int PAGE_SIZE = 20;
  private static final int PREFETCH_DISTANCE = 5;
  final LiveData<PagingData<Pokemon>> pokemonList;
  private final MutableLiveData<Boolean> query = new MutableLiveData<>();

  public MainViewModel() {
    PokemonDao pokemonDao =
        PokemonDataBase.getInstance(PokemonApplication.getContext()).pokemonDao();
    pokemonList =
        Transformations.switchMap(
            query, orderByDesc -> getPagedListLiveData(pokemonDao, orderByDesc));
  }

  private LiveData<PagingData<Pokemon>> getPagedListLiveData(
      PokemonDao pokemonDao, Boolean orderByDesc) {
    return PagingLiveData.getLiveData(liveData(pokemonDao, orderByDesc));
  }

  private Pager<Integer, Pokemon> liveData(PokemonDao pokemonDao, Boolean orderByDesc) {
    PagingConfig pagingConfig = new PagingConfig(PAGE_SIZE, PREFETCH_DISTANCE, true);
    return new Pager<>(
        pagingConfig, INITIAL_LOAD_KEY, remoteMediator(), () -> factory(pokemonDao, orderByDesc));
  }

  @Nullable
  private RemoteMediator<Integer, Pokemon> remoteMediator() {
    return null;
  }

  private PagingSource<Integer, Pokemon> factory(PokemonDao pokemonDao, Boolean orderByDesc) {
    return orderByDesc ? pokemonDao.allByDesc() : pokemonDao.allByAsc();
  }

  public void postValue(boolean orderByDesc) {
    query.postValue(orderByDesc);
  }
}
