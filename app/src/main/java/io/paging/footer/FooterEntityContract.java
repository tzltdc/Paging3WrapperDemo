package io.paging.footer;

import paging.wrapper.model.ui.FooterEntity;

public interface FooterEntityContract {

  void removeFooter();

  void addFooter(FooterEntity footerEntity);

  void refreshFooter(FooterEntity footerEntity);
}
