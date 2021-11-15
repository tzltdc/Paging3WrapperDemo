package io.husayn.paging_library_sample.listing;

import static io.husayn.paging_library_sample.utilities.TextUtility.toThreeDigitNumber;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.husayn.paging_library_sample.R;
import paging.wrapper.model.data.Pokemon;

class PokemonViewHolder extends RecyclerView.ViewHolder {

  private static final String POKE_IMAGE_URL_PREFIX =
      "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
  private static final String POKE_IMAGE_URL_SUFFIX = ".png";

  private final TextView pokemonIdTextView;
  private final ImageView pokemonSpriteImageView;
  private final TextView pokemonNameTextView;
  private final OnItemClickCallback onItemClickCallback;

  @AssistedInject
  PokemonViewHolder(@Assisted View itemView, OnItemClickCallback onItemClickCallback) {
    super(itemView);
    this.onItemClickCallback = onItemClickCallback;
    pokemonIdTextView = itemView.findViewById(R.id.tv_pokemon_id);
    pokemonNameTextView = itemView.findViewById(R.id.tv_pokemon);
    pokemonSpriteImageView = itemView.findViewById(R.id.iv_pokemon);
  }

  void bindTo(Pokemon pokemon) {
    itemView.setTag(pokemon.id);
    pokemonIdTextView.setText(
        itemView.getContext().getString(R.string.pokemon_id, toThreeDigitNumber(pokemon.id)));
    pokemonNameTextView.setText(pokemon.name);
    Glide.with(itemView.getContext())
        .load(pokemonSpriteUrl(pokemon.id))
        .into(pokemonSpriteImageView);

    itemView.setOnClickListener(v -> onItemClickCallback.onItemClick(pokemon));
  }

  public interface OnItemClickCallback {

    void onItemClick(Pokemon pokemon);
  }

  private String pokemonSpriteUrl(int id) {
    return POKE_IMAGE_URL_PREFIX + id + POKE_IMAGE_URL_SUFFIX;
  }

  void clear() {
    itemView.invalidate();
    pokemonIdTextView.invalidate();
    pokemonNameTextView.invalidate();
    pokemonSpriteImageView.invalidate();
  }
}
