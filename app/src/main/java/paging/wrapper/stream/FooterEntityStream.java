package paging.wrapper.stream;

import com.google.common.base.Optional;
import paging.wrapper.model.ui.FooterEntity;

public interface FooterEntityStream {

  void accept(Optional<FooterEntity> newFootEntity);
}
