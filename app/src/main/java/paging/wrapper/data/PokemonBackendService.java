package paging.wrapper.data;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;
import paging.wrapper.db.RemoteDataServer;
import paging.wrapper.model.data.PagingQueryParam;
import paging.wrapper.model.data.PagingRequest;
import paging.wrapper.model.data.Pokemon;
import paging.wrapper.model.data.PokemonDto;
import timber.log.Timber;

public class PokemonBackendService {

  private final RemoteDataServer remoteDataServer;

  @Inject
  public PokemonBackendService(RemoteDataServer remoteDataServer) {
    this.remoteDataServer = remoteDataServer;
  }

  public Single<PokemonDto> query(PagingRequest pagingRequest) {
    return Observable.fromIterable(remoteDataServer.get())
        .doOnSubscribe(PokemonBackendService::logSubscribed)
        .filter(item -> validItem(item, pagingRequest.pagingQueryParam()))
        .skip(pagingRequest.offSet())
        .take(pagingRequest.queryConfig().countPerPage())
        .toList()
        .map(PokemonDto::create)
        .doOnDispose(PokemonBackendService::logOnDispose);
  }

  private static void logOnDispose() {
    Timber.i("[ttt]:server concludes the request.");
  }

  private static void logSubscribed(Disposable disposable) {
    Timber.i("[ttt]:server receives the request.");
  }

  public static boolean validItem(Pokemon pokemon, PagingQueryParam pagingQueryParam) {
    String searchKey = pagingQueryParam.searchKey();
    return searchKey == null || pokemon.name.contains(searchKey);
  }
}
