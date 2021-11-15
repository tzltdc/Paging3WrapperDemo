package io.paging.footer;

import io.husayn.paging_library_sample.ActivityScope;
import io.view.header.FooterEntity;
import javax.inject.Inject;
import timber.log.Timber;

@ActivityScope
public class FooterEntityDelegate {

  private FooterEntity footerEntity = null;

  @Inject
  public FooterEntityDelegate() {
    Timber.i("FooterEntityDelegate created:%s", this);
  }

  public FooterEntity getFooterEntity() {
    return footerEntity;
  }

  public boolean footerDataPresent() {
    return footerEntity != null;
  }

  public void setFooterEntity(FooterEntity footerEntity) {
    this.footerEntity = footerEntity;
  }
}
