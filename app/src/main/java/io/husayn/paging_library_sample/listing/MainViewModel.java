package io.husayn.paging_library_sample.listing;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PagingSource;
import io.husayn.paging_library_sample.PokemonApplication;
import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.data.PokemonDao;
import io.husayn.paging_library_sample.data.PokemonDataBase;

public class MainViewModel extends ViewModel {

  private static final int INITIAL_LOAD_KEY = 0;
  private static final int PAGE_SIZE = 20;
  private static final int PREFETCH_DISTANCE = 5;
  final LiveData<PagedList<Pokemon>> pokemonList;
  private final MutableLiveData<Boolean> query = new MutableLiveData<>();

  public MainViewModel() {
    PokemonDao pokemonDao =
        PokemonDataBase.getInstance(PokemonApplication.getContext()).pokemonDao();
    PagedList.Config config =
        new PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .setEnablePlaceholders(true)
            .build();

    pokemonList =
        Transformations.switchMap(query, orderByDesc -> liveData(pokemonDao, config, orderByDesc));
  }

  private LiveData<PagedList<Pokemon>> liveData(
      PokemonDao pokemonDao, PagedList.Config config, Boolean orderByDesc) {

    return new LivePagedListBuilder<>(() -> factory(pokemonDao, orderByDesc), config)
        .setInitialLoadKey(INITIAL_LOAD_KEY)
        .build();
  }

  private PagingSource<Integer, Pokemon> factory(PokemonDao pokemonDao, Boolean orderByDesc) {
    return orderByDesc ? pokemonDao.allByDesc() : pokemonDao.allByAsc();
  }

  public void postValue(boolean orderByDesc) {
    query.postValue(orderByDesc);
  }
}
