package io.husayn.paging_library_sample.listing;

import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.data.RemoteDataServer;
import io.reactivex.Observable;
import java.util.List;
import timber.log.Timber;

class OffsetHelper {

  /** Temporarily using the remote service to identify the offset position */
  public static long offset(Pokemon lastFetchedAsTarget, PagingQueryParam query) {
    long loaded = offSet(lastFetchedAsTarget, query);
    Timber.i(
        "offset loaded count:%s, query:%s, last item:%s",
        loaded, query.searchKey(), lastFetchedAsTarget);
    return loaded;
  }

  private static Long offSet(Pokemon lastFetchedAsTarget, PagingQueryParam query) {
    List<Pokemon> matched =
        Observable.fromIterable(RemoteDataServer.all())
            .filter(item -> PokemonBackendService.validItem(item, query))
            .toList()
            .blockingGet();
    return Observable.fromIterable(matched)
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
