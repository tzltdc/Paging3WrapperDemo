package paging.wrapper.mapper;

import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import paging.wrapper.data.PokemonBackendService;
import paging.wrapper.db.RemoteDataServer;
import paging.wrapper.model.data.PagingQueryParam;
import paging.wrapper.model.data.Pokemon;
import timber.log.Timber;

public class OffsetHelper {

  private final RemoteDataServer remoteDataServer;

  @Inject
  public OffsetHelper(RemoteDataServer remoteDataServer) {
    this.remoteDataServer = remoteDataServer;
  }

  /** Temporarily using the remote service to identify the offset position */
  public int offset(Pokemon lastFetchedAsTarget, PagingQueryParam query) {
    int loaded = offSet(lastFetchedAsTarget, query);
    Timber.i(
        "offset loaded count:%s, query:%s, last item:%s",
        loaded, query.searchKey(), lastFetchedAsTarget);
    return loaded;
  }

  private int offSet(Pokemon lastFetchedAsTarget, PagingQueryParam query) {
    List<Pokemon> matched =
        Observable.fromIterable(remoteDataServer.get())
            .filter(item -> PokemonBackendService.validItem(item, query))
            .toList()
            .blockingGet();
    return Observable.fromIterable(matched)
        .takeUntil(
            pokemon -> {
              return matchTarget(lastFetchedAsTarget, pokemon);
            })
        .count()
        .blockingGet()
        .intValue();
  }

  private static boolean matchTarget(Pokemon lastFetchedAsTarget, Pokemon pokemon) {
    return pokemon.equals(lastFetchedAsTarget);
  }
}
