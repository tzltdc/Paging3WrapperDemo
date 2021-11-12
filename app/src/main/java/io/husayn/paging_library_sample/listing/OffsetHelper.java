package io.husayn.paging_library_sample.listing;

import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.data.RemoteDataServer;
import io.reactivex.Observable;

class OffsetHelper {

  /** Temporarily using the remote service to identify the offset position */
  public static long offset(Pokemon lastFetchedAsTarget, PagingQuery query) {
    Observable<Pokemon> pokemonObservable =
        Observable.fromIterable(RemoteDataServer.all())
            .filter(item -> ExampleBackendService.validItem(lastFetchedAsTarget, query))
            .takeUntil(
                pokemon -> {
                  return pokemon.equals(lastFetchedAsTarget);
                });
    return pokemonObservable.count().blockingGet();
  }
}
