package io.husayn.paging_library_sample.listing;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

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
        PokemonDao pokemonDao = PokemonDataBase.getInstance(PokemonApplication.getContext()).pokemonDao();
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setPrefetchDistance(PREFETCH_DISTANCE)
                .setEnablePlaceholders(true)
                .build();


        pokemonList = Transformations.switchMap(query, orderByDesc -> {
            if (orderByDesc) {
                return new LivePagedListBuilder<>(pokemonDao.allByDesc(), config).setInitialLoadKey(INITIAL_LOAD_KEY).build();
            } else {
                return new LivePagedListBuilder<>(pokemonDao.allByAsc(), config).setInitialLoadKey(INITIAL_LOAD_KEY).build();
            }
        });


    }

    public void postValue(boolean orderByDesc) {
        query.postValue(orderByDesc);
    }

}