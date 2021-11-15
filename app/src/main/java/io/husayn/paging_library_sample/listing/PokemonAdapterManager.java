package io.husayn.paging_library_sample.listing;

import io.husayn.paging_library_sample.ActivityScope;
import io.paging.footer.FooterEntityDelegate;
import io.stream.footer_entity.FooterModel;
import io.view.header.FooterEntity;
import javax.inject.Inject;
import timber.log.Timber;

@ActivityScope
public class PokemonAdapterManager {

  private final PokemonAdapter pokemonAdapter;
  private final FooterEntityDelegate footerEntityDelegate;

  @Inject
  public PokemonAdapterManager(
      PokemonAdapter pokemonAdapter, FooterEntityDelegate footerEntityDelegate) {
    this.pokemonAdapter = pokemonAdapter;
    this.footerEntityDelegate = footerEntityDelegate;
    Timber.i("PokemonAdapterManager created:%s", this);
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
    setFooterEntity(null);
    pokemonAdapter.notifyItemRemoved(pokemonAdapter.dataItemCount());
  }

  private void setFooterEntity(FooterEntity footerEntity) {
    footerEntityDelegate.setFooterEntity(footerEntity);
  }

  private void addFooter(FooterEntity footerEntity) {
    setFooterEntity(footerEntity);
    pokemonAdapter.notifyItemInserted(pokemonAdapter.dataItemCount());
  }

  private void refreshFooter(FooterEntity footerEntity) {
    setFooterEntity(footerEntity);
    pokemonAdapter.notifyItemChanged(pokemonAdapter.dataItemCount());
  }
}
