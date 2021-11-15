package paging.wrapper.mapper;

import io.reactivex.Observable;
import java.util.List;
import paging.wrapper.data.PokemonBackendService;
import paging.wrapper.db.RemoteDataServer;
import paging.wrapper.model.data.PagingQueryParam;
import paging.wrapper.model.data.Pokemon;
import timber.log.Timber;

public class OffsetHelper {

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
