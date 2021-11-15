package paging.wrapper.model.data;

import androidx.annotation.Nullable;
import androidx.paging.PagingSource;
import com.google.auto.value.AutoValue;
import kotlin.jvm.functions.Function0;
import paging.wrapper.data.PokemonRemoteMediator;

@AutoValue
public abstract class PagerContext {

  @Nullable
  public abstract PokemonRemoteMediator optionalRemoteMediator();

  public abstract Function0<PagingSource<Integer, Pokemon>> localPagingSource();

  public static PagerContext create(
      Function0<PagingSource<Integer, Pokemon>> pagingSourceFunction,
      @Nullable PokemonRemoteMediator optionalRemoteMediator) {
    return new AutoValue_PagerContext(optionalRemoteMediator, pagingSourceFunction);
  }
}
