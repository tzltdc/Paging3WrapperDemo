package io.view.header;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import io.husayn.paging_library_sample.R;
import io.paging.footer.ClickActionContract;
import io.view.header.HeaderEntity.Empty;
import timber.log.Timber;

/**
 * A header view contract that implicitly expect all of its children view in constructor will be
 * present.
 */
public class HeaderViewContract {

  private final ProgressBar pb_header;
  private final TextView tv_header_error_hint;
  private final TextView tv_header_empty_hint;
  private final Button btn_header_retry;
  private final View fl_header_loading;
  private final View fl_header_error;
  private final View fl_header_empty;
  private final ClickActionContract clickActionContract;

  public HeaderViewContract(View headerView, ClickActionContract clickActionContract) {
    this.clickActionContract = clickActionContract;
    fl_header_loading = headerView.findViewById(R.id.fl_header_loading);
    fl_header_error = headerView.findViewById(R.id.fl_header_error);
    fl_header_empty = headerView.findViewById(R.id.fl_header_empty);
    pb_header = headerView.findViewById(R.id.pb_header);
    tv_header_error_hint = headerView.findViewById(R.id.tv_header_error_hint);
    tv_header_empty_hint = headerView.findViewById(R.id.tv_header_empty_hint);
    btn_header_retry = headerView.findViewById(R.id.btn_header_retry);
  }

  public void bind(HeaderEntity entity) {
    Timber.i("bind HeaderEntity:%s", entity);
    switch (entity.state()) {
      case LOADING:
        bindLoading();
        break;
      case ERROR:
        bindError(entity.error());
        break;
      case EMPTY:
        bindEmpty(entity.empty());
        break;
    }
  }

  private void bindEmpty(Empty empty) {
    fl_header_error.setVisibility(View.GONE);
    fl_header_loading.setVisibility(View.GONE);
    fl_header_empty.setVisibility(View.VISIBLE);
    tv_header_empty_hint.setText(empty.message());
  }

  private void bindError(ErrorData errorData) {
    fl_header_empty.setVisibility(View.GONE);
    fl_header_loading.setVisibility(View.GONE);
    fl_header_error.setVisibility(View.VISIBLE);
    tv_header_error_hint.setText(errorData.message());
    String buttonText = errorData.buttonText();
    if (buttonText == null) {
      btn_header_retry.setVisibility(View.GONE);
      btn_header_retry.setOnClickListener(null);
    } else {
      btn_header_retry.setVisibility(View.VISIBLE);
      btn_header_retry.setText(buttonText);
      btn_header_retry.setOnClickListener(view -> clickActionContract.retry());
    }
  }

  private void bindLoading() {
    pb_header.setVisibility(View.VISIBLE);
    fl_header_error.setVisibility(View.GONE);
    fl_header_loading.setVisibility(View.VISIBLE);
    fl_header_empty.setVisibility(View.GONE);
  }
}
