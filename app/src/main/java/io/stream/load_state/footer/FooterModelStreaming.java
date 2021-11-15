package io.stream.load_state.footer;

import com.google.common.base.Optional;
import io.reactivex.Observable;
import io.stream.footer_entity.FooterEntityStreaming;
import javax.inject.Inject;
import paging.wrapper.model.ui.FooterEntity;
import paging.wrapper.model.ui.FooterModel;

public class FooterModelStreaming {

  private final FooterEntityStreaming footerEntityStreaming;

  @Inject
  public FooterModelStreaming(FooterEntityStreaming footerEntityStreaming) {
    this.footerEntityStreaming = footerEntityStreaming;
  }

  public Observable<FooterModel> streaming() {
    return footerEntityStreaming
        .newFooterEntity()
        .map(this::asFooterModel)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .distinctUntilChanged();
  }

  private Optional<FooterModel> asFooterModel(Optional<FooterEntity> newFooterEntity) {
    FooterEntity newFooter = newFooterEntity.orNull();
    FooterEntity oldFooter = footerEntityStreaming.currentFooterEntity().orNull();
    if (newFooter != null && oldFooter != null) {
      return newFooter.equals(oldFooter)
          ? Optional.absent()
          : Optional.of(FooterModel.ofToBeRefreshed(newFooter));
    } else if (newFooter == null) {
      return Optional.of(FooterModel.ofToBeRemoved());
    } else {
      return Optional.of(FooterModel.ofToBeAdded(newFooter));
    }
  }
}
