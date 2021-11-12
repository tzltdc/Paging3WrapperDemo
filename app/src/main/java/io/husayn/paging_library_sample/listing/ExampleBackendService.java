package io.husayn.paging_library_sample.listing;

import io.husayn.paging_library_sample.data.RemoteDataServer;
import io.reactivex.Observable;
import io.reactivex.Single;

public class ExampleBackendService {

  public Single<SearchPokemonResponse> searchPokemons(Boolean query, Integer loadKey) {
    throw new RuntimeException();
  }

  public Single<SearchPokemonResponse> fetch(PagingRequest pagingRequest) {
    return Observable.fromIterable(RemoteDataServer.all())
        .filter(pokemon -> pokemon.name.contains(pagingRequest.pagingQuery.searchKey))
        .skip(pagingRequest.offSet)
        .take(pagingRequest.queryConfig.countPerPage)
        .toList()
        .map(SearchPokemonResponse::new);
  }
}
