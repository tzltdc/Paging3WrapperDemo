package paging.wrapper.data;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingSource;
import androidx.paging.rxjava2.PagingRx;
import io.reactivex.Observable;
import javax.inject.Inject;
import kotlin.jvm.functions.Function0;
import paging.wrapper.demo.ui.query.QueryStreaming;
import paging.wrapper.di.thread.WorkerScheduler;
import paging.wrapper.model.data.PagerContext;
import paging.wrapper.model.data.PagingQueryContext;
import paging.wrapper.model.data.PagingQueryParam;
import paging.wrapper.model.data.Pokemon;
import timber.log.Timber;

public class PagingPokemonRepo {

  private static final int INITIAL_LOAD_KEY = 0;

  private final PokemonRepo pokemonRepo;
  private final PagingConfig androidPagingConfig;
  private final PokemonRemoteMediatorFactory pokemonRemoteMediatorFactory;
  private final QueryStreaming queryStreaming;
  private final WorkerScheduler workerScheduler;

  @Inject
  public PagingPokemonRepo(
      PokemonRepo pokemonRepo,
      PagingConfig androidPagingConfig,
      PokemonRemoteMediatorFactory pokemonRemoteMediatorFactory,
      QueryStreaming queryStreaming,
      WorkerScheduler workerScheduler) {
    this.pokemonRepo = pokemonRepo;
    this.androidPagingConfig = androidPagingConfig;
    this.pokemonRemoteMediatorFactory = pokemonRemoteMediatorFactory;
    this.queryStreaming = queryStreaming;
    this.workerScheduler = workerScheduler;
  }

  public Observable<PagingData<Pokemon>> rxPagingData() {
    return queryStreaming
        .streaming()
        .doOnNext(this::logOnQueryEmitted)
        .observeOn(workerScheduler.get())
        .doOnNext(this::logOnThreadSwitched)
        .map(this::pagerContext)
        .map(this::pager)
        .switchMap(PagingRx::getObservable)
        .doOnNext(this::logOnRepoEmitted);
  }

  private void logOnRepoEmitted(PagingData<Pokemon> pokemonPagingData) {
    Timber.i("[ttt]:Returned user query with PagingData :%s", pokemonPagingData);
  }

  private void logOnThreadSwitched(PagingQueryContext context) {
    Timber.i("[ttt]:user query context emitted after switch thread:%s", context);
  }

  private void logOnQueryEmitted(PagingQueryContext context) {
    Timber.i("[ttt]:user query context emitted prior switch thread:%s", context);
  }

  private PagerContext pagerContext(PagingQueryContext query) {
    return PagerContext.create(pagingSourceFunction(query.param()), optionalRemoteMediator(query));
  }

  private PokemonRemoteMediator optionalRemoteMediator(PagingQueryContext query) {
    return pokemonRemoteMediatorFactory.create(query);
  }

  private Function0<PagingSource<Integer, Pokemon>> pagingSourceFunction(PagingQueryParam query) {
    return () -> pokemonRepo.pokemonLocalPagingSource(query);
  }

  private Pager<Integer, Pokemon> pager(PagerContext context) {
    return new Pager<>(
        androidPagingConfig,
        INITIAL_LOAD_KEY,
        context.optionalRemoteMediator(),
        context.localPagingSource());
  }
}
