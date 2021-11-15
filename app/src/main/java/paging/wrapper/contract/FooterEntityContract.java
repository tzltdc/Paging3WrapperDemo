package paging.wrapper.contract;

import paging.wrapper.model.ui.FooterEntity;

public interface FooterEntityContract {

  void removeFooter();

  void addFooter(FooterEntity footerEntity);

  void refreshFooter(FooterEntity footerEntity);
}
