package paging.wrapper.worker;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import com.uber.autodispose.ScopeProvider;
import javax.inject.Inject;
import paging.wrapper.app.AutoDisposeWorker;
import paging.wrapper.contract.FooterEntityContract;
import paging.wrapper.model.ui.FooterModel;
import paging.wrapper.stream.FooterModelStreaming;
import timber.log.Timber;

public class FooterModelWorker implements AutoDisposeWorker {

  private final FooterModelStreaming footerModelStreaming;
  private final FooterEntityContract footerEntityContract;

  @Inject
  public FooterModelWorker(
      FooterModelStreaming footerModelStreaming, FooterEntityContract footerEntityContract) {
    this.footerModelStreaming = footerModelStreaming;
    this.footerEntityContract = footerEntityContract;
  }

  @Override
  public void attach(ScopeProvider scopeProvider) {
    footerModelStreaming
        .streaming()
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
}
