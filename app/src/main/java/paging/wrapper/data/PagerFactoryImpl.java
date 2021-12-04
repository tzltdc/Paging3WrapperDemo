package paging.wrapper.data;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import javax.inject.Inject;
import paging.wrapper.model.data.PagingQueryContext;
import paging.wrapper.model.data.Pokemon;

public class PagerFactoryImpl implements PagerFactory {

  private static final int INITIAL_LOAD_KEY = 0;

  private final PagingConfig androidPagingConfig;
  private final PokemonRemoteMediatorFactory pokemonRemoteMediatorFactory;
  private final LocalPagingSourceFunctionFactory localPagingSourceFunctionFactory;

  @Inject
  public PagerFactoryImpl(
      PagingConfig androidPagingConfig,
      LocalPagingSourceFunctionFactory localPagingSourceFunctionFactory,
      PokemonRemoteMediatorFactory pokemonRemoteMediatorFactory) {
    this.androidPagingConfig = androidPagingConfig;
    this.pokemonRemoteMediatorFactory = pokemonRemoteMediatorFactory;
    this.localPagingSourceFunctionFactory = localPagingSourceFunctionFactory;
  }

  public Pager<Integer, Pokemon> pager(PagingQueryContext query) {
    return new Pager<>(
        androidPagingConfig,
        INITIAL_LOAD_KEY,
        pokemonRemoteMediatorFactory.create(query),
        localPagingSourceFunctionFactory.create(query.param()));
  }
}
