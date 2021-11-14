package io.husayn.paging_library_sample.listing;

import static io.husayn.paging_library_sample.PokemonApplication.getContext;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import io.husayn.paging_library_sample.R;
import io.husayn.paging_library_sample.data.Pokemon;
import io.view.header.FooterEntity;
import io.view.header.FooterViewHolder;
import javax.inject.Inject;
import timber.log.Timber;

public class PokemonAdapter extends PagingDataAdapter<Pokemon, ViewHolder> {

  private FooterEntity footerEntity = null;
  private final PokemonViewHolderFactory pokemonViewHolderFactory;

  @Inject
  public PokemonAdapter(PokemonViewHolderFactory pokemonViewHolderFactory) {
    super(Pokemon.DIFF_CALLBACK);
    this.pokemonViewHolderFactory = pokemonViewHolderFactory;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (footerEntity == null) {
      View itemView =
          LayoutInflater.from(getContext()).inflate(R.layout.item_pokemon, parent, false);
      return pokemonViewHolderFactory.create(itemView);
    } else {
      return new FooterViewHolder(parent);
    }
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    int viewType = getItemViewType(position);
    if (viewType == ItemViewType.ITEM_VIEW_TYPE_FOOTER) {
      ((FooterViewHolder) viewHolder).bind(footerEntity);
    } else if (viewHolder instanceof PokemonViewHolder) {
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
    return showFooterView() && position == super.getItemCount();
  }

  @Override
  public int getItemCount() {
    return super.getItemCount() + (showFooterView() ? 1 : 0);
  }

  private boolean showFooterView() {
    // For now, always show the footer as long it is present.
    return footerEntity != null;
  }

  public void bind(@Nullable FooterEntity newFooterEntity) {
    Timber.i("bindFooterEntity:%s", newFooterEntity);
    FooterEntity previousState = this.footerEntity;
    boolean showFooterViewBefore = showFooterView();
    this.footerEntity = newFooterEntity;
    boolean showFooterViewNow = showFooterView();
    if (showFooterViewBefore != showFooterViewNow) {
      if (showFooterViewBefore) {
        notifyItemRemoved(super.getItemCount());
      } else {
        notifyItemInserted(super.getItemCount());
      }
    } else if (showFooterViewNow && !previousState.equals(newFooterEntity)) {
      notifyItemChanged(super.getItemCount());
    }
  }
}
