package io.husayn.paging_library_sample.listing;

import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.data.RemoteDataServer;
import io.reactivex.Observable;
import io.reactivex.Single;

public class ExampleBackendService {

  public static Single<SearchPokemonResponse> query(PagingRequest pagingRequest) {
    return Observable.fromIterable(RemoteDataServer.all())
        .filter(pokemon -> validItem(pokemon, pagingRequest.pagingQuery))
        .skip(pagingRequest.offSet)
        .take(pagingRequest.queryConfig.countPerPage)
        .toList()
        .map(SearchPokemonResponse::new);
  }

  public static boolean validItem(Pokemon pokemon, PagingQuery pagingQuery) {
    String searchKey = pagingQuery.searchKey;
    return searchKey == null || pokemon.name.contains(searchKey);
  }
}
