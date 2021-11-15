package io.stream.footer_entity;

import com.google.common.base.Optional;
import io.reactivex.Observable;
import paging.wrapper.model.ui.FooterEntity;

public interface FooterEntityStreaming {

  /** @return The latest {@link FooterEntity} that is to be rendered into UI. */
  Observable<Optional<FooterEntity>> newFooterEntity();

  /** @return the current {@link FooterEntity} that has already been rendered into UI. */
  Optional<FooterEntity> currentFooterEntity();
}
