package paging.wrapper.data;

import androidx.paging.PagingSource;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import kotlin.jvm.functions.Function0;
import paging.wrapper.model.data.PagingQueryContext;
import paging.wrapper.model.data.Pokemon;

public class RemotePagingFunction implements Function0<PagingSource<Integer, Pokemon>> {

  private final RemotePokenmonPagingSourceFactory factory;
  private final PagingQueryContext pagingQueryContext;

  @AssistedInject
  public RemotePagingFunction(
      @Assisted PagingQueryContext pagingQueryContext, RemotePokenmonPagingSourceFactory factory) {
    this.factory = factory;
    this.pagingQueryContext = pagingQueryContext;
  }

  @Override
  public PagingSource<Integer, Pokemon> invoke() {
    return factory.create(pagingQueryContext);
  }
}
