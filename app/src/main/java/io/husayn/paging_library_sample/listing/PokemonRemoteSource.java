package io.husayn.paging_library_sample.listing;

import io.reactivex.Single;
import io.thread.WorkerScheduler;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import timber.log.Timber;

public class PokemonRemoteSource {

  private static final boolean ENFORCE_TIMEOUT_ERROR = false;
  private static final int DELAY_IN_MS = 1100;

  private static int timeout() {
    return ENFORCE_TIMEOUT_ERROR ? (DELAY_IN_MS / 2) : (DELAY_IN_MS * 2);
  }

  private final WorkerScheduler workerScheduler;

  @Inject
  public PokemonRemoteSource(WorkerScheduler workerScheduler) {
    this.workerScheduler = workerScheduler;
  }

  public Single<PageActionResult> fetch(PagingRequest pagingRequest) {
    return PokemonBackendService.query(pagingRequest)
        .subscribeOn(workerScheduler.get())
        .delay(DELAY_IN_MS, TimeUnit.MILLISECONDS, workerScheduler.get())
        .timeout(timeout(), TimeUnit.MILLISECONDS, workerScheduler.get())
        .doOnSuccess(this::logOnSuccess)
        .doOnError(this::logOnError)
        .map(response -> PageActionResult.create(response, pagingRequest));
  }

  private void logOnError(Throwable throwable) {
    Timber.e(throwable, "remote data fetch error");
  }

  private void logOnSuccess(PokemonDto response) {
    Timber.i("remote data fetch success with size:%s", response.list().size());
  }
}
