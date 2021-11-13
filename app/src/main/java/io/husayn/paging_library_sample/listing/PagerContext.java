package io.husayn.paging_library_sample.listing;

import androidx.paging.PagingSource;
import com.google.auto.value.AutoValue;
import io.husayn.paging_library_sample.data.Pokemon;
import kotlin.jvm.functions.Function0;

@AutoValue
public abstract class PagerContext {

  public abstract PokemonRemoteMediator pokemonRemoteMediator();

  public abstract Function0<PagingSource<Integer, Pokemon>> localPagingSource();

  public static PagerContext create(
      PokemonRemoteMediator pokemonRemoteMediator,
      Function0<PagingSource<Integer, Pokemon>> pagingSourceFunction) {
    return new AutoValue_PagerContext(pokemonRemoteMediator, pagingSourceFunction);
  }
}
