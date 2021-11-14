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
      ((FooterViewHolder) viewHolder).bind(footerEntity);
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
    return position == super.getItemCount();
  }

  @Override
  public int getItemCount() {
    return super.getItemCount() + (footerDataPresent() ? 1 : 0);
  }

  private boolean footerDataPresent() {
    // For now, always show the footer as long it is present.
    return footerEntity != null;
  }

  public void bind(@Nullable FooterEntity newFooterEntity) {
    Timber.i("bindFooterEntity:%s", newFooterEntity);
    FooterEntity previousFooter = this.footerEntity;
    boolean oldFooterVisible = footerDataPresent();
    this.footerEntity = newFooterEntity;
    boolean newFooterVisible = footerDataPresent();
    if (oldFooterVisible != newFooterVisible) {
      if (oldFooterVisible) {
        removeFooter();
      } else {
        showFooter();
      }
    } else if (newFooterVisible && !previousFooter.equals(newFooterEntity)) {
      refreshFooter();
    }
  }

  private void removeFooter() {
    notifyItemRemoved(super.getItemCount());
  }

  private void showFooter() {
    notifyItemInserted(super.getItemCount());
  }

  private void refreshFooter() {
    notifyItemChanged(super.getItemCount());
  }
}
