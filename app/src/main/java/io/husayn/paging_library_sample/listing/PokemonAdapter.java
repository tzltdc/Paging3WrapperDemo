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
import io.view.header.FooterViewHolder;
import javax.inject.Inject;
import paging.wrapper.model.ui.FooterEntity;
import timber.log.Timber;

@ActivityScope
public class PokemonAdapter extends PagingDataAdapter<Pokemon, ViewHolder>
    implements PagingAdapterContract {

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
      return new FooterViewHolder(
          LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false), this);
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
    return footerDataPresent() && endPosition(position);
  }

  private boolean endPosition(int position) {
    return position == footEntityPosition();
  }

  @Override
  public int getItemCount() {
    return super.getItemCount() + (footerDataPresent() ? 1 : 0);
  }

  private int footEntityPosition() {
    return super.getItemCount();
  }

  private boolean footerDataPresent() {
    return this.footerEntity != null;
  }

  @Override
  public void removeFooter() {
    this.footerEntity = null;
    notifyItemRemoved(footEntityPosition());
  }

  @Override
  public void addFooter(FooterEntity footerEntity) {
    this.footerEntity = footerEntity;
    notifyItemInserted(footEntityPosition());
  }

  @Override
  public void refreshFooter(FooterEntity footerEntity) {
    this.footerEntity = footerEntity;
    notifyItemChanged(footEntityPosition());
  }
}
