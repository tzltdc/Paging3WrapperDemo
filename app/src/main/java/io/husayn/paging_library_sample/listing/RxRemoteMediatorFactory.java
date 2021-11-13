package io.husayn.paging_library_sample.listing;

import dagger.assisted.AssistedFactory;

@AssistedFactory
interface RxRemoteMediatorFactory {

  ExampleRemoteMediator create(PagingQuery pagingQuery);
}
