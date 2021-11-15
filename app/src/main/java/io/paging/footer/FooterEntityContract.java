package io.paging.footer;

import io.view.header.FooterEntity;

public interface FooterEntityContract {

  void removeFooter();

  void addFooter(FooterEntity footerEntity);

  void refreshFooter(FooterEntity footerEntity);
}
