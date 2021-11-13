package io.husayn.paging_library_sample.listing;

import dagger.assisted.AssistedFactory;

@AssistedFactory
interface PokemonRemoteMediatorFactory {

  PokemonRemoteMediator create(PagingQuery pagingQuery);
}
