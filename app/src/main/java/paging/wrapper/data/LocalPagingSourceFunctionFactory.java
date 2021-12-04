package paging.wrapper.data;

import dagger.assisted.AssistedFactory;
import paging.wrapper.model.data.PagingQueryParam;

@AssistedFactory
interface LocalPagingSourceFunctionFactory {

  LocalPagingSourceFunction create(PagingQueryParam queryParam);
}
