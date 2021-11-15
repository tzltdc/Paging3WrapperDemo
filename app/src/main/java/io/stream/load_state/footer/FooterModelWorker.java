package io.stream.load_state.footer;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import com.google.common.base.Optional;
import com.uber.autodispose.ScopeProvider;
import io.husayn.paging_library_sample.AutoDisposeWorker;
import io.paging.footer.FooterEntityContract;
import io.stream.footer_entity.FooterEntityStreaming;
import io.stream.footer_entity.FooterModel;
import io.view.header.FooterEntity;
import javax.inject.Inject;
import timber.log.Timber;

public class FooterModelWorker implements AutoDisposeWorker {

  private final FooterEntityStreaming footerEntityStreaming;
  private final FooterEntityContract footerEntityContract;

  @Inject
  public FooterModelWorker(
      FooterEntityStreaming footerEntityStreaming, FooterEntityContract footerEntityContract) {
    this.footerEntityStreaming = footerEntityStreaming;
    this.footerEntityContract = footerEntityContract;
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

  private void bindFooterModel(FooterModel model) {
    Timber.i("bindFooterModel:%s", model);
    switch (model.status()) {
      case TO_BE_REMOVED:
        footerEntityContract.removeFooter();
        break;
      case TO_BE_ADDED:
        footerEntityContract.addFooter(model.toBeAdded());
        break;
      case TO_BE_REFRESHED:
        footerEntityContract.refreshFooter(model.toBeRefreshed());
        break;
    }
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
