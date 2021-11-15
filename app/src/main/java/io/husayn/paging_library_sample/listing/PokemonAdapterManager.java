package io.husayn.paging_library_sample.listing;

import io.husayn.paging_library_sample.ActivityScope;
import io.paging.footer.FooterEntityContract;
import io.stream.footer_entity.FooterModel;
import javax.inject.Inject;
import timber.log.Timber;

@ActivityScope
public class PokemonAdapterManager {

  private final FooterEntityContract footerEntityContract;

  @Inject
  public PokemonAdapterManager(FooterEntityContract footerEntityContract) {
    this.footerEntityContract = footerEntityContract;
    Timber.i("PokemonAdapterManager created:%s", this);
  }

  public void bind(FooterModel model) {
    Timber.i("bindFooterModel:%s", model);
    switch (model.status()) {
      case TO_BE_REMOVED:
        footerEntityContract.removeFooter();
        break;
      case TO_BE_ADDED:
        footerEntityContract.addFooter(model.toBeAdded());
        break;
      case TO_BE_REFRESHED:
        footerEntityContract.refreshFooter(model.toBeRefreshed());
        break;
    }
  }
}
