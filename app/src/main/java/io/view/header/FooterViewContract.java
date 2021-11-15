package io.view.header;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import io.husayn.paging_library_sample.R;
import io.paging.footer.ClickActionContract;
import io.view.header.FooterEntity.LoadingMore;
import timber.log.Timber;

public class FooterViewContract {

  private final TextView tv_footer_error_hint;
  private final TextView tv_footer_no_more_hint;
  private final TextView tv_footer_loading_hint;
  private final Button btn_footer_retry;
  private final View fl_footer_loading;
  private final View fl_footer_error;
  private final View fl_footer_no_more;
  private final ClickActionContract actionContract;

  public FooterViewContract(View itemView, ClickActionContract actionContract) {
    this.actionContract = actionContract;
    // top view
    fl_footer_error = itemView.findViewById(R.id.fl_footer_error);
    fl_footer_loading = itemView.findViewById(R.id.fl_footer_loading);
    fl_footer_no_more = itemView.findViewById(R.id.fl_footer_no_more);

    // children view
    tv_footer_loading_hint = itemView.findViewById(R.id.tv_footer_loading_hint);
    btn_footer_retry = itemView.findViewById(R.id.btn_footer_retry);
    tv_footer_error_hint = itemView.findViewById(R.id.tv_footer_error_hint);
    tv_footer_no_more_hint = itemView.findViewById(R.id.tv_footer_no_more_hint);
  }

  public void bind(FooterEntity entity) {
    Timber.i("bind FooterEntity:%s", entity);
    switch (entity.state()) {
      case LOADING_MORE:
        bindLoading(entity.loadingMore());
        break;
      case ERROR:
        bindError(entity.error());
        break;
      case NO_MORE:
        bindNoMore(entity.noMore());
        break;
    }
  }

  private void bindNoMore(FooterEntity.NoMore noMore) {
    fl_footer_error.setVisibility(View.GONE);
    fl_footer_loading.setVisibility(View.GONE);
    fl_footer_no_more.setVisibility(View.VISIBLE);
    tv_footer_no_more_hint.setText(noMore.message());
  }

  private void bindError(ErrorData error) {
    fl_footer_loading.setVisibility(View.GONE);
    fl_footer_no_more.setVisibility(View.GONE);
    fl_footer_error.setVisibility(View.VISIBLE);
    tv_footer_error_hint.setText(error.message());
    String buttonText = error.buttonText();
    if (buttonText != null) {
      btn_footer_retry.setVisibility(View.VISIBLE);
      btn_footer_retry.setText(buttonText);
      btn_footer_retry.setOnClickListener(view -> actionContract.retry());
    } else {
      btn_footer_retry.setOnClickListener(null);
      btn_footer_retry.setVisibility(View.GONE);
    }
  }

  private void bindLoading(LoadingMore loadingMore) {
    fl_footer_error.setVisibility(View.GONE);
    fl_footer_loading.setVisibility(View.VISIBLE);
    fl_footer_no_more.setVisibility(View.GONE);
    String message = loadingMore.message();
    if (message == null) {
      tv_footer_loading_hint.setVisibility(View.GONE);
    } else {
      tv_footer_loading_hint.setVisibility(View.VISIBLE);
      tv_footer_error_hint.setText(message);
    }
  }
}
