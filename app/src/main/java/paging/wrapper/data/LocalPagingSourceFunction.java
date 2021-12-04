package paging.wrapper.data;

import androidx.paging.PagingSource;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import kotlin.jvm.functions.Function0;
import paging.wrapper.model.data.PagingQueryParam;
import paging.wrapper.model.data.Pokemon;

class LocalPagingSourceFunction implements Function0<PagingSource<Integer, Pokemon>> {

  private final PagingQueryParam query;
  private final PokemonRepo pokemonRepo;

  @AssistedInject
  public LocalPagingSourceFunction(@Assisted PagingQueryParam query, PokemonRepo pokemonRepo) {
    this.query = query;
    this.pokemonRepo = pokemonRepo;
  }

  @Override
  public PagingSource<Integer, Pokemon> invoke() {
    return pokemonRepo.pokemonLocalPagingSource(query);
  }
}
