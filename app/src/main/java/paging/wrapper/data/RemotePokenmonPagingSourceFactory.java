package paging.wrapper.data;

import dagger.assisted.AssistedFactory;
import paging.wrapper.model.data.PagingQueryContext;

@AssistedFactory
interface RemotePokenmonPagingSourceFactory {

  RemotePokenmonPagingSource create(PagingQueryContext context);
}
