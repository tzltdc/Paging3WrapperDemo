package io.husayn.paging_library_sample.listing;

import dagger.assisted.AssistedFactory;
import paging.wrapper.model.data.PagingQueryContext;

@AssistedFactory
interface PokemonRemoteMediatorFactory {

  PokemonRemoteMediator create(PagingQueryContext context);
}
