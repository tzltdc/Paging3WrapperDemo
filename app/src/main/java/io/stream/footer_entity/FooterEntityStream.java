package io.stream.footer_entity;

import com.google.common.base.Optional;
import io.view.header.FooterEntity;

public interface FooterEntityStream {

  void accept(Optional<FooterEntity> newFootEntity);
}
