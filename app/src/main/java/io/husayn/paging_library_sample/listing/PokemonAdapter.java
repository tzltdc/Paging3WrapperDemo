package io.husayn.paging_library_sample.listing;

import static io.husayn.paging_library_sample.PokemonApplication.getContext;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import io.husayn.paging_library_sample.R;
import io.husayn.paging_library_sample.data.Pokemon;
import javax.inject.Inject;
import timber.log.Timber;

public class PokemonAdapter extends PagingDataAdapter<Pokemon, PokemonViewHolder> {

  private final PokemonViewHolderFactory pokemonViewHolderFactory;

  @Inject
  public PokemonAdapter(PokemonViewHolderFactory pokemonViewHolderFactory) {
    super(Pokemon.DIFF_CALLBACK);
    this.pokemonViewHolderFactory = pokemonViewHolderFactory;
  }

  @Override
  public int getItemViewType(int position) {
    return ItemViewType.ITEM_VIEW_TYPE_BODY;
  }

  @NonNull
  @Override
  public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_pokemon, parent, false);
    return pokemonViewHolderFactory.create(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
    Timber.w("onBindViewHolder:%s:%s", position, this);
    Pokemon pokemon = getItem(position);
    if (pokemon != null) {
      holder.bindTo(pokemon);
    } else {
      holder.clear();
    }
  }
}
