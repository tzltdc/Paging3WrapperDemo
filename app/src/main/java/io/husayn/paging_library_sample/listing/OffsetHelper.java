package io.husayn.paging_library_sample.listing;

import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.data.RemoteDataServer;
import io.reactivex.Observable;

class OffsetHelper {

  /** Temporarily using the remote service to identify the offset position */
  public static long offset(Pokemon target, PagingQuery query) {
    Observable<Pokemon> pokemonObservable =
        Observable.fromIterable(RemoteDataServer.all())
            .filter(item -> ExampleBackendService.validItem(target, query))
            .takeUntil(
                pokemon -> {
                  return pokemon.equals(target);
                });
    return pokemonObservable.count().blockingGet();
  }
}
