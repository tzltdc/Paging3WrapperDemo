package io.husayn.paging_library_sample.listing;

import static io.husayn.paging_library_sample.PokemonApplication.getContext;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import io.husayn.paging_library_sample.R;
import io.husayn.paging_library_sample.data.Pokemon;
import javax.inject.Inject;

public class PokemonAdapter extends PagingDataAdapter<Pokemon, ViewHolder> {

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
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_pokemon, parent, false);
    return pokemonViewHolderFactory.create(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    if (holder instanceof PokemonViewHolder) {
      PokemonViewHolder pokemonViewHolder = (PokemonViewHolder) holder;
      Pokemon pokemon = getItem(position);
      if (pokemon != null) {
        pokemonViewHolder.bindTo(pokemon);
      } else {
        pokemonViewHolder.clear();
      }
    }
  }
}
