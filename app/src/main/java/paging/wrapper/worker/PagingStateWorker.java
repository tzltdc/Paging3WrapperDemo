package paging.wrapper.worker;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import androidx.pagingx.CombinedLoadStates;
import com.uber.autodispose.ScopeProvider;
import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import paging.wrapper.app.AutoDisposeWorker;
import paging.wrapper.di.app.AppGson;
import paging.wrapper.di.thread.AppScheduler;
import paging.wrapper.stream.LoadStateStreaming;

public class PagingStateWorker implements AutoDisposeWorker {

  private final AppGson appGson;
  private final AppScheduler appScheduler;
  private final LoadStateStreaming loadStateStreaming;

  @Inject
  public PagingStateWorker(
      AppGson appGson, AppScheduler appScheduler, LoadStateStreaming loadStateStreaming) {
    this.appGson = appGson;
    this.appScheduler = appScheduler;
    this.loadStateStreaming = loadStateStreaming;
  }

  @Override
  public void attach(ScopeProvider scopeProvider) {
    streaming().map(this::asJson).as(autoDisposable(scopeProvider)).subscribe(this::accept);
  }

  /**
   * Why we have two distinctUntilChanged here? Here is the theory:
   *
   * <p>In the first distinctUntilChanged, it guarantee different items to be emitted, the series
   * could be A, B, A, B, A, B
   *
   * <p>After going through throttleFirst, it could be A, A, A, A if B is always emitted immediately
   * after A. And then A is emitted 100 milliseconds later after B.
   *
   * <p>Hence, two distinctUntilChanged make the item deterministic.
   */
  private Observable<CombinedLoadStates> streaming() {
    return loadStateStreaming
        .raw()
        .distinctUntilChanged()
        .throttleFirst(100, TimeUnit.MILLISECONDS, appScheduler.worker())
        .distinctUntilChanged();
  }

  private void accept(String json) {
    System.out.println(json);
  }

  private String asJson(CombinedLoadStates states) {
    return appGson.get().toJson(states);
  }
}
