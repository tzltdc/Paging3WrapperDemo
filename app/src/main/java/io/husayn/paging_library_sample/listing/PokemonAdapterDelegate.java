package io.husayn.paging_library_sample.listing;

import io.husayn.paging_library_sample.ActivityScope;
import io.view.header.FooterEntity;
import javax.inject.Inject;
import timber.log.Timber;

@ActivityScope
public class PokemonAdapterDelegate {

  private FooterEntity footerEntity = null;

  @Inject
  public PokemonAdapterDelegate() {
    Timber.i("PokemonAdapterDelegate created:%s", this);
  }

  public FooterEntity getFooterEntity() {
    return footerEntity;
  }

  public void setFooterEntity(FooterEntity footerEntity) {
    this.footerEntity = footerEntity;
  }
}
