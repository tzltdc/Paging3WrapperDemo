package paging.wrapper.demo;

import android.view.View;
import dagger.assisted.AssistedFactory;

@AssistedFactory
interface PokemonViewHolderFactory {

  PokemonViewHolder create(View itemView);
}
