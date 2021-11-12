package io.husayn.paging_library_sample.listing;

import io.reactivex.Single;

public class ExampleBackendService {

  public Single<SearchPokemonResponse> searchPokemons(Boolean query, Integer loadKey) {
    throw new RuntimeException();
  }

  public Single<SearchPokemonResponse> fetch(PagingRequest pagingRequest) {
    throw new RuntimeException();
  }
}
