package io.stream.load_state.footer;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import com.google.common.base.Optional;
import com.uber.autodispose.ScopeProvider;
import io.husayn.paging_library_sample.AutoDisposeWorker;
import io.husayn.paging_library_sample.listing.MainUI;
import io.stream.footer_entity.FooterEntityStreaming;
import io.stream.footer_entity.FooterModel;
import io.view.header.FooterEntity;
import javax.inject.Inject;

public class FooterModelWorker implements AutoDisposeWorker {

  private final MainUI mainUI;
  private final FooterEntityStreaming footerEntityStreaming;

  @Inject
  public FooterModelWorker(MainUI mainUI, FooterEntityStreaming footerEntityStreaming) {
    this.mainUI = mainUI;
    this.footerEntityStreaming = footerEntityStreaming;
  }

  @Override
  public void attach(ScopeProvider scopeProvider) {
    footerEntityStreaming
        .newFooterEntity()
        .map(this::asFooterModel)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .distinctUntilChanged()
        .as(autoDisposable(scopeProvider))
        .subscribe(this::bindFooterModel);
  }

  private void bindFooterModel(FooterModel footerModel) {
    mainUI.bindFooterModel(footerModel);
  }

  private Optional<FooterModel> asFooterModel(Optional<FooterEntity> newFooterModel) {
    FooterEntity newFooterEntity = newFooterModel.orNull();
    FooterEntity previousFooter = footerEntityStreaming.currentFooterEntity().orNull();
    if (newFooterEntity != null && previousFooter != null) {
      return newFooterEntity.equals(previousFooter)
          ? Optional.absent()
          : Optional.of(FooterModel.ofToBeRefreshed(newFooterEntity));
    } else if (newFooterEntity == null) {
      return Optional.of(FooterModel.ofToBeRemoved());
    } else {
      return Optional.of(FooterModel.ofToBeAdded(newFooterEntity));
    }
  }
}
