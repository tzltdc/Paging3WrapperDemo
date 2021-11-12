package io.husayn.paging_library_sample.listing;

import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.data.RemoteDataServer;
import io.reactivex.Observable;
import timber.log.Timber;

class OffsetHelper {

  /** Temporarily using the remote service to identify the offset position */
  public static long offset(Pokemon lastFetchedAsTarget, PagingQuery query) {
    long loaded = offSet(lastFetchedAsTarget, query);
    Timber.i("tonny loaded:%s, query:%s", loaded, query.searchKey());
    return loaded;
  }

  private static Long offSet(Pokemon lastFetchedAsTarget, PagingQuery query) {
    return Observable.fromIterable(RemoteDataServer.all())
        .filter(item -> ExampleBackendService.validItem(lastFetchedAsTarget, query))
        .takeUntil(
            pokemon -> {
              return matchTarget(lastFetchedAsTarget, pokemon);
            })
        .count()
        .blockingGet();
  }

  private static boolean matchTarget(Pokemon lastFetchedAsTarget, Pokemon pokemon) {
    return pokemon.equals(lastFetchedAsTarget);
  }
}
