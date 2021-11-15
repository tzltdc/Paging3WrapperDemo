package paging.wrapper.data;

import androidx.paging.RemoteMediator.InitializeAction;
import io.reactivex.Single;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

class InitializeActionMapper {

  public static final boolean DISABLE_CACHE = true;

  public static Single<InitializeAction> initializeSingle() {
    return lastUpdatedSingle()
        .map(InitializeActionMapper::initializeAction)
        .doOnSuccess(InitializeActionMapper::logOnInitialize);
  }

  private static void logOnInitialize(InitializeAction initializeAction) {
    Timber.i("logOnInitialize:%s", initializeAction);
  }

  private static InitializeAction initializeAction(long expireTs) {
    long timeToLive = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS);
    if (valid(System.currentTimeMillis(), expireTs, timeToLive)) {
      // Cached data is up-to-date, so there is no need to re-fetch
      // from the network.
      return InitializeAction.SKIP_INITIAL_REFRESH;
    } else {
      // Need to refresh cached data from network; returning
      // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
      // APPEND and PREPEND from running until REFRESH succeeds.
      return InitializeAction.LAUNCH_INITIAL_REFRESH;
    }
  }

  private static boolean valid(long currentTs, long expireTs, long timeToLive) {
    return currentTs + timeToLive < expireTs;
  }

  private static Single<Long> lastUpdatedSingle() {
    //    return pokemonDao.lastUpdatedSingle();
    return Single.just(DISABLE_CACHE ? expired() : never());
  }

  private static long never() {
    return Long.MAX_VALUE;
  }

  private static long expired() {
    return System.currentTimeMillis();
  }
}
