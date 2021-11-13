package io.husayn.paging_library_sample.listing;

import android.view.View;
import dagger.assisted.AssistedFactory;

@AssistedFactory
interface PokemonViewHolderFactory {

  PokemonViewHolder create(View itemView);
}
