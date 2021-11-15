package io.stream.footer_entity;

import com.google.common.base.Optional;
import com.jakewharton.rxrelay2.BehaviorRelay;
import io.reactivex.Observable;
import paging.wrapper.model.ui.FooterEntity;

public class FooterEntityStreamImpl implements FooterEntityStreaming, FooterEntityStream {

  private final BehaviorRelay<Optional<FooterEntity>> current =
      BehaviorRelay.createDefault(Optional.absent());
  private final BehaviorRelay<Optional<FooterEntity>> newest = BehaviorRelay.create();

  @Override
  public Observable<Optional<FooterEntity>> newFooterEntity() {
    return newest.hide();
  }

  @Override
  public Optional<FooterEntity> currentFooterEntity() {
    return current.getValue();
  }

  @Override
  public void accept(Optional<FooterEntity> newFootEntity) {
    current.accept(newestValue());
    newest.accept(newFootEntity);
  }

  private Optional<FooterEntity> newestValue() {
    @SuppressWarnings("OptionalAssignedToNull")
    FooterEntity newestValue = newest.getValue() == null ? null : newest.getValue().orNull();
    return Optional.fromNullable(newestValue);
  }
}
