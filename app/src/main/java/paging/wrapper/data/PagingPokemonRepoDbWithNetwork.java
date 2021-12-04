package paging.wrapper.data;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava2.PagingRx;
import io.reactivex.Observable;
import javax.inject.Inject;
import paging.wrapper.demo.ui.query.QueryStreaming;
import paging.wrapper.di.thread.AppScheduler;
import paging.wrapper.model.data.PagingQueryContext;
import paging.wrapper.model.data.Pokemon;
import timber.log.Timber;

public class PagingPokemonRepoDbWithNetwork implements PagingPokemonRepo {

  private static final int INITIAL_LOAD_KEY = 0;

  private final PagingConfig androidPagingConfig;
  private final PokemonRemoteMediatorFactory pokemonRemoteMediatorFactory;
  private final QueryStreaming queryStreaming;
  private final AppScheduler appScheduler;
  private final LocalPagingSourceFunctionFactory localPagingSourceFunctionFactory;

  @Inject
  public PagingPokemonRepoDbWithNetwork(
      PagingConfig androidPagingConfig,
      LocalPagingSourceFunctionFactory localPagingSourceFunctionFactory,
      QueryStreaming queryStreaming,
      AppScheduler appScheduler,
      PokemonRemoteMediatorFactory pokemonRemoteMediatorFactory) {
    this.androidPagingConfig = androidPagingConfig;
    this.pokemonRemoteMediatorFactory = pokemonRemoteMediatorFactory;
    this.queryStreaming = queryStreaming;
    this.appScheduler = appScheduler;
    this.localPagingSourceFunctionFactory = localPagingSourceFunctionFactory;
  }

  @Override
  public Observable<PagingData<Pokemon>> rxPagingData() {
    return queryStreaming
        .streaming()
        .doOnNext(this::logOnQueryEmitted)
        .observeOn(appScheduler.worker())
        .doOnNext(this::logOnThreadSwitched)
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

  private Pager<Integer, Pokemon> pager(PagingQueryContext query) {
    return new Pager<>(
        androidPagingConfig,
        INITIAL_LOAD_KEY,
        pokemonRemoteMediatorFactory.create(query),
        localPagingSourceFunctionFactory.create(query.param()));
  }
}
