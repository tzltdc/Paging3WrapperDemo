package paging.wrapper.worker;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import com.uber.autodispose.ScopeProvider;
import javax.inject.Inject;
import paging.wrapper.app.AutoDisposeWorker;
import paging.wrapper.data.PagingPokemonRepo;
import paging.wrapper.di.thread.MainScheduler;
import paging.wrapper.stream.PagingDataStream;

public class PagingDataWorker implements AutoDisposeWorker {

  private final PagingPokemonRepo pagingPokemonRepo;
  private final PagingDataStream pagingDataStream;

  @Inject
  public PagingDataWorker(PagingPokemonRepo pagingPokemonRepo, PagingDataStream pagingDataStream) {
    this.pagingPokemonRepo = pagingPokemonRepo;
    this.pagingDataStream = pagingDataStream;
  }

  @Override
  public void attach(ScopeProvider scopeProvider) {
    pagingPokemonRepo
        .rxPagingData()
        .observeOn(MainScheduler.get())
        .as(autoDisposable(scopeProvider))
        .subscribe(pagingDataStream::accept);
  }
}
