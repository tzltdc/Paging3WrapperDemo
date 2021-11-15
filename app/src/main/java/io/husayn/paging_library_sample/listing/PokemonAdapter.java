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
import io.stream.footer_entity.FooterModel;
import io.view.header.FooterEntity;
import io.view.header.FooterViewHolder;
import javax.inject.Inject;
import timber.log.Timber;

@ActivityScope
public class PokemonAdapter extends PagingDataAdapter<Pokemon, ViewHolder> {

  private FooterEntity footerEntity = null;
  private final PokemonViewHolderFactory pokemonViewHolderFactory;

  @Inject
  public PokemonAdapter(PokemonViewHolderFactory pokemonViewHolderFactory) {
    super(Pokemon.DIFF_CALLBACK);
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
    return position == dataItemCount();
  }

  @Override
  public int getItemCount() {
    return dataItemCount() + (footerDataPresent() ? 1 : 0);
  }

  private boolean footerDataPresent() {
    // For now, always show the footer as long it is present.
    return footerEntity != null;
  }

  public void bind(FooterModel model) {
    Timber.i("bindFooterModel:%s", model);
    switch (model.status()) {
      case TO_BE_REMOVED:
        removeFooter();
        break;
      case TO_BE_ADDED:
        addFooter(model.toBeAdded());
        break;
      case TO_BE_REFRESHED:
        refreshFooter(model.toBeRefreshed());
        break;
    }
  }

  private void removeFooter() {
    this.footerEntity = null;
    notifyItemRemoved(dataItemCount());
  }

  public int dataItemCount() {
    return super.getItemCount();
  }

  private void addFooter(FooterEntity footerEntity) {
    this.footerEntity = footerEntity;
    notifyItemInserted(dataItemCount());
  }

  private void refreshFooter(FooterEntity footerEntity) {
    this.footerEntity = footerEntity;
    notifyItemChanged(dataItemCount());
  }
}
