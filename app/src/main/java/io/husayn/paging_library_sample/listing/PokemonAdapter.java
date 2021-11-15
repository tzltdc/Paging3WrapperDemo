package io.husayn.paging_library_sample.listing;

import static io.husayn.paging_library_sample.PokemonApplication.getContext;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import io.husayn.paging_library_sample.ActivityScope;
import io.husayn.paging_library_sample.R;
import io.husayn.paging_library_sample.data.Pokemon;
import io.view.header.FooterEntity;
import io.view.header.FooterViewHolder;
import javax.inject.Inject;
import timber.log.Timber;

@ActivityScope
public class PokemonAdapter extends PagingDataAdapter<Pokemon, ViewHolder> {

  private final PokemonAdapterDelegate pokemonAdapterDelegate;
  private final PokemonViewHolderFactory pokemonViewHolderFactory;

  @Inject
  public PokemonAdapter(
      PokemonAdapterDelegate pokemonAdapterDelegate,
      PokemonViewHolderFactory pokemonViewHolderFactory) {
    super(Pokemon.DIFF_CALLBACK);
    this.pokemonAdapterDelegate = pokemonAdapterDelegate;
    this.pokemonViewHolderFactory = pokemonViewHolderFactory;
    Timber.i("PokemonAdapter created:%s", this);
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (viewType == ItemViewType.ITEM_VIEW_TYPE_BODY) {
      View itemView =
          LayoutInflater.from(getContext()).inflate(R.layout.item_pokemon, parent, false);
      return pokemonViewHolderFactory.create(itemView);
    } else if (viewType == ItemViewType.ITEM_VIEW_TYPE_FOOTER) {
      return new FooterViewHolder(parent);
    } else {
      throw new RuntimeException("Unsupported viewType:" + viewType);
    }
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    int viewType = getItemViewType(position);
    if (viewType == ItemViewType.ITEM_VIEW_TYPE_FOOTER) {
      ((FooterViewHolder) viewHolder).bind(getFooterEntity());
    } else if (viewType == ItemViewType.ITEM_VIEW_TYPE_BODY) {
      PokemonViewHolder pokemonViewHolder = (PokemonViewHolder) viewHolder;
      Pokemon pokemon = getItem(position);
      if (pokemon != null) {
        pokemonViewHolder.bindTo(pokemon);
      } else {
        pokemonViewHolder.clear();
      }
    }
  }

  @Override
  public int getItemViewType(int position) {
    return footerViewType(position)
        ? ItemViewType.ITEM_VIEW_TYPE_FOOTER
        : ItemViewType.ITEM_VIEW_TYPE_BODY;
  }

  private boolean footerViewType(int position) {
    return extraPosition(position) && footerDataPresent();
  }

  private boolean extraPosition(int position) {
    return position == dataItemCount();
  }

  @Override
  public int getItemCount() {
    return dataItemCount() + (footerDataPresent() ? 1 : 0);
  }

  private boolean footerDataPresent() {
    // For now, always show the footer as long it is present.
    return getFooterEntity() != null;
  }

  private FooterEntity getFooterEntity() {
    return pokemonAdapterDelegate.getFooterEntity();
  }

  public int dataItemCount() {
    return super.getItemCount();
  }
}
