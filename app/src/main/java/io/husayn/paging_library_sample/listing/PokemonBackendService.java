package io.husayn.paging_library_sample.listing;

import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.data.RemoteDataServer;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class PokemonBackendService {

  public static Single<PokemonDto> query(PagingRequest pagingRequest) {
    return Observable.fromIterable(RemoteDataServer.all())
        .doOnSubscribe(PokemonBackendService::logSubscribed)
        .filter(item -> validItem(item, pagingRequest.pagingQueryParam()))
        .skip(pagingRequest.offSet())
        .take(pagingRequest.queryConfig().countPerPage())
        .toList()
        .map(PokemonDto::create)
        .doOnDispose(PokemonBackendService::logOnDispose);
  }

  private static void logOnDispose() {
    Timber.i("[thread:%s]:server returns the request.", Thread.currentThread().getName());
  }

  private static void logSubscribed(Disposable disposable) {
    Timber.i("[thread:%s]:server receives the request.", Thread.currentThread().getName());
  }

  public static boolean validItem(Pokemon pokemon, PagingQueryParam pagingQueryParam) {
    String searchKey = pagingQueryParam.searchKey();
    return searchKey == null || pokemon.name.contains(searchKey);
  }
}
