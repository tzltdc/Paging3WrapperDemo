package paging.wrapper.data;

import androidx.paging.Pager;
import androidx.paging.PagingData;
import androidx.paging.rxjava2.PagingRx;
import io.reactivex.Observable;
import javax.inject.Inject;
import paging.wrapper.demo.ui.query.QueryStreaming;
import paging.wrapper.di.app.ActivityScope;
import paging.wrapper.di.thread.AppScheduler;
import paging.wrapper.model.data.PagingQueryContext;
import paging.wrapper.model.data.Pokemon;
import timber.log.Timber;

@ActivityScope
public class PagingPokemonRepo {

  private final QueryStreaming queryStreaming;
  private final AppScheduler appScheduler;
  private final PagerFactory pagerFactory;

  @Inject
  public PagingPokemonRepo(
      QueryStreaming queryStreaming, AppScheduler appScheduler, PagerFactory pagerFactory) {
    this.queryStreaming = queryStreaming;
    this.appScheduler = appScheduler;
    this.pagerFactory = pagerFactory;
  }

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

  public Pager<Integer, Pokemon> pager(PagingQueryContext query) {
    return pagerFactory.pager(query);
  }
}
