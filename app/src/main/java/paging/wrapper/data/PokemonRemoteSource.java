package paging.wrapper.data;

import io.reactivex.Single;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import paging.wrapper.di.thread.AppScheduler;
import paging.wrapper.model.data.PageActionResult;
import paging.wrapper.model.data.PagingRequest;
import paging.wrapper.model.data.PokemonDto;
import timber.log.Timber;

public class PokemonRemoteSource {

  private static final boolean ENFORCE_TIMEOUT_ERROR = false;
  private static final int DELAY_IN_MS = 1100;

  private final AppScheduler appScheduler;
  private final PokemonBackendService pokemonBackendService;

  @Inject
  public PokemonRemoteSource(
      AppScheduler appScheduler, PokemonBackendService pokemonBackendService) {
    this.appScheduler = appScheduler;
    this.pokemonBackendService = pokemonBackendService;
  }

  public Single<PageActionResult> fetch(PagingRequest pagingRequest) {
    return pokemonBackendService
        .query(pagingRequest)
        .subscribeOn(appScheduler.worker())
        .delay(DELAY_IN_MS, TimeUnit.MILLISECONDS, appScheduler.worker())
        .timeout(timeout(), TimeUnit.MILLISECONDS, appScheduler.worker())
        .doOnSuccess(this::logOnSuccess)
        .doOnError(this::logOnError)
        .map(response -> PageActionResult.create(response, pagingRequest));
  }

  private void logOnError(Throwable throwable) {
    Timber.e(throwable, "[ttt]: Running error during fetching remote data source.");
  }

  private static int timeout() {
    return ENFORCE_TIMEOUT_ERROR ? (DELAY_IN_MS / 2) : (DELAY_IN_MS * 2);
  }

  private void logOnSuccess(PokemonDto response) {
    Timber.i("[ttt]:Succuessfully fetched remote data with record size:%s", response.list().size());
  }
}
