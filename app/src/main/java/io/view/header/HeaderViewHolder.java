package io.view.header;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import io.husayn.paging_library_sample.R;
import io.view.header.HeaderEntity.Empty;
import io.view.header.HeaderEntity.Error.ErrorAction;
import timber.log.Timber;

public class HeaderViewHolder extends RecyclerView.ViewHolder {

  private final ProgressBar pb_header;
  private final TextView tv_header_error_hint;
  private final TextView tv_header_empty_hint;
  private final Button btn_header_retry;
  private final View fl_header_loading;
  private final View fl_header_error;
  private final View fl_header_empty;

  public HeaderViewHolder(ViewGroup parent) {
    super(LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false));
    fl_header_loading = itemView.findViewById(R.id.fl_header_loading);
    fl_header_error = itemView.findViewById(R.id.fl_header_error);
    fl_header_empty = itemView.findViewById(R.id.fl_header_empty);
    pb_header = itemView.findViewById(R.id.pb_header);
    tv_header_error_hint = itemView.findViewById(R.id.tv_header_error_hint);
    tv_header_empty_hint = itemView.findViewById(R.id.tv_header_empty_hint);
    btn_header_retry = itemView.findViewById(R.id.btn_header_retry);
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

  private void bindError(HeaderEntity.Error error) {
    fl_header_empty.setVisibility(View.GONE);
    fl_header_loading.setVisibility(View.GONE);
    fl_header_error.setVisibility(View.VISIBLE);
    tv_header_error_hint.setText(error.message());
    ErrorAction action = error.action();
    if (action == null) {
      btn_header_retry.setVisibility(View.GONE);
      btn_header_retry.setOnClickListener(null);
    } else {
      btn_header_retry.setVisibility(View.VISIBLE);
      btn_header_retry.setOnClickListener(view -> action.callback().onClick(error));
    }
  }

  private void bindLoading() {
    pb_header.setVisibility(View.VISIBLE);
    fl_header_error.setVisibility(View.GONE);
    fl_header_loading.setVisibility(View.VISIBLE);
    fl_header_empty.setVisibility(View.GONE);
  }
}
