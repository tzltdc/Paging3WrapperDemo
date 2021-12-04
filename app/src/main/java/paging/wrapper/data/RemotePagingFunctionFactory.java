package paging.wrapper.data;

import dagger.assisted.AssistedFactory;
import paging.wrapper.model.data.PagingQueryContext;

@AssistedFactory
public interface RemotePagingFunctionFactory {

  RemotePagingFunction create(PagingQueryContext context);
}
