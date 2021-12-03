package paging.wrapper.worker;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import com.uber.autodispose.ScopeProvider;
import javax.inject.Inject;
import paging.wrapper.app.AutoDisposeWorker;
import paging.wrapper.data.PagingPokemonRepo;
import paging.wrapper.di.thread.AppScheduler;
import paging.wrapper.stream.PagingDataStream;

public class PagingDataWorker implements AutoDisposeWorker {

  private final AppScheduler appScheduler;
  private final PagingPokemonRepo pagingPokemonRepo;
  private final PagingDataStream pagingDataStream;

  @Inject
  public PagingDataWorker(
      AppScheduler appScheduler,
      PagingPokemonRepo pagingPokemonRepo,
      PagingDataStream pagingDataStream) {
    this.appScheduler = appScheduler;
    this.pagingPokemonRepo = pagingPokemonRepo;
    this.pagingDataStream = pagingDataStream;
  }

  @Override
  public void attach(ScopeProvider scopeProvider) {
    pagingPokemonRepo
        .rxPagingData()
        .observeOn(appScheduler.ui())
        .as(autoDisposable(scopeProvider))
        .subscribe(pagingDataStream::accept);
  }
}
