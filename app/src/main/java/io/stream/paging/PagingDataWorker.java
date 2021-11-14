package io.stream.paging;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import com.uber.autodispose.ScopeProvider;
import io.husayn.paging_library_sample.AutoDisposeWorker;
import io.husayn.paging_library_sample.listing.PagingPokemonRepo;
import io.thread.MainScheduler;
import javax.inject.Inject;

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
