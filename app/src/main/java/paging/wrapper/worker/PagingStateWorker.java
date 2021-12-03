package paging.wrapper.worker;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import androidx.pagingx.CombinedLoadStates;
import com.uber.autodispose.ScopeProvider;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import paging.wrapper.app.AutoDisposeWorker;
import paging.wrapper.di.app.AppGson;
import paging.wrapper.stream.LoadStateStreaming;

public class PagingStateWorker implements AutoDisposeWorker {

  private final AppGson appGson;
  private final LoadStateStreaming loadStateStreaming;

  @Inject
  public PagingStateWorker(AppGson appGson, LoadStateStreaming loadStateStreaming) {
    this.appGson = appGson;
    this.loadStateStreaming = loadStateStreaming;
  }

  @Override
  public void attach(ScopeProvider scopeProvider) {
    loadStateStreaming
        .raw()
        .distinctUntilChanged()
        .throttleFirst(100, TimeUnit.MILLISECONDS)
        .map(this::asJson)
        .as(autoDisposable(scopeProvider))
        .subscribe(this::accept);
  }

  private void accept(String json) {
    System.out.println(json);
  }

  private String asJson(CombinedLoadStates states) {
    return appGson.get().toJson(states);
  }
}
