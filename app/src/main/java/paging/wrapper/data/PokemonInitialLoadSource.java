package paging.wrapper.data;

import androidx.paging.RemoteMediator.MediatorResult;
import io.reactivex.Single;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import paging.wrapper.demo.ui.query.FilterOptionProvider;
import paging.wrapper.di.app.ActivityScope;
import paging.wrapper.di.thread.AppScheduler;
import paging.wrapper.mapper.PagingRequestMapper;
import paging.wrapper.model.data.PagingQueryContext;
import paging.wrapper.model.data.PagingQueryParam;
import paging.wrapper.model.data.PagingRequest;
import timber.log.Timber;

@ActivityScope
public class PokemonInitialLoadSource {

  private final PokemonMediatorResultRepo pokemonMediatorResultRepo;
  private final AppScheduler scheduler;
  private int retryCount = 0;

  @Inject
  public PokemonInitialLoadSource(
      PokemonMediatorResultRepo pokemonMediatorResultRepo, AppScheduler scheduler) {
    this.pokemonMediatorResultRepo = pokemonMediatorResultRepo;
    this.scheduler = scheduler;
    Timber.i("PokemonInitialLoadSource created:%s", this);
  }

  /**
   * The network load method takes an optional after=<Pokemon.id> parameter. For every page after
   * the first, pass the last Pokemon ID to let it continue from where it left off. For REFRESH,
   * pass null to load the first page.
   */
  public Single<MediatorResult> load(PagingQueryContext query) {
    return simulateError(query.description())
        ? simulateError(query.param())
        : execute(query.param());
  }

  private Single<MediatorResult> execute(PagingQueryParam query) {
    PagingRequest pagingRequest = PagingRequestMapper.defaultPagingRequest(query);
    Timber.i("[ttt]: PokemonInitialLoadSource is to trigger pagingRequest:%s", pagingRequest);
    return pokemonMediatorResultRepo.request(pagingRequest);
  }

  private boolean simulateError(String desc) {
    return FilterOptionProvider.INITIAL_LOAD_ERROR.equals(desc) && retryCount++ % 2 == 0;
  }

  private Single<MediatorResult> simulateError(PagingQueryParam query) {
    Timber.i("[ttt]: PokemonInitialLoadSource is to return a simulated error with query:%s", query);
    return Single.timer(1000, TimeUnit.MILLISECONDS, scheduler.worker())
        .map(ignored -> new MediatorResult.Error(new RuntimeException("Simulated initial error")));
  }
}
