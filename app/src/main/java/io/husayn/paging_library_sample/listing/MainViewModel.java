package io.husayn.paging_library_sample.listing;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
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

    public MainViewModel() {
        PokemonDao pokemonDao = PokemonDataBase.getInstance(PokemonApplication.getContext()).pokemonDao();
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setPrefetchDistance(PREFETCH_DISTANCE)
                .setEnablePlaceholders(true)
                .build();

        DataSource.Factory<Integer, Pokemon> pokemons = pokemonDao.pokemons();
        pokemonList = new LivePagedListBuilder<>(pokemons, config).setInitialLoadKey(INITIAL_LOAD_KEY).build();

    }
}
//a=0，1，2
//b=0,1,2,3,4,5, 6
//Case 1 : b=0 1/7
// a=1,a=2 2/3
//P1 = 1/7 * 2/3 = 2/21
//Case 2 :  b=1 1/7
// a= 2 1/3
// p2: 1/7 * 1/3 = 1/21
//P= p1 + p2 = 2/21 + 1/21 = 3/21 = 1/7