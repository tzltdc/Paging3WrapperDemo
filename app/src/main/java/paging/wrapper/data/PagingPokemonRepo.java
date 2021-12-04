package paging.wrapper.data;

import androidx.paging.PagingData;
import io.reactivex.Observable;
import paging.wrapper.model.data.Pokemon;

public interface PagingPokemonRepo {

  Observable<PagingData<Pokemon>> rxPagingData();
}
