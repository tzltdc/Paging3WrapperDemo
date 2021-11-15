package io.husayn.paging_library_sample.listing;

import io.husayn.paging_library_sample.data.RemoteDataServer;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import paging.wrapper.model.data.PagingQueryParam;
import paging.wrapper.model.data.PagingRequest;
import paging.wrapper.model.data.Pokemon;
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
