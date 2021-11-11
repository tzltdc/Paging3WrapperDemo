package io.husayn.paging_library_sample.listing;

import static io.husayn.paging_library_sample.PokemonApplication.getContext;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.husayn.paging_library_sample.R;
import io.husayn.paging_library_sample.data.Pokemon;

public class PokemonAdapter extends PagedListAdapter<Pokemon, PokemonViewHolder> {

  private PokemonViewHolder.OnItemClickCallback onItemClickCallback;

  public PokemonAdapter(PokemonViewHolder.OnItemClickCallback onItemClickCallback) {
    super(Pokemon.DIFF_CALLBACK);
    this.onItemClickCallback = onItemClickCallback;
  }

  @NonNull
  @Override
  public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_pokemon, parent, false);
    return new PokemonViewHolder(itemView, onItemClickCallback);
  }

  @Override
  public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
    Pokemon pokemon = getItem(position);
    if (pokemon != null) {
      holder.bindTo(pokemon);
    } else {
      holder.clear();
    }
  }
}
