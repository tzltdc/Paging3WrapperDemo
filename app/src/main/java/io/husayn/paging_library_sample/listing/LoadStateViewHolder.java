package io.husayn.paging_library_sample.listing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.RecyclerView;
import io.husayn.paging_library_sample.R;

class LoadStateViewHolder extends RecyclerView.ViewHolder {

  private final ProgressBar progress_bar;
  private final TextView header_error;
  private final Button btn_retry;
  private final OnClickListener retryCallback;

  LoadStateViewHolder(ViewGroup parent, View.OnClickListener retryCallback) {
    super(LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false));
    this.retryCallback = retryCallback;
    progress_bar = itemView.findViewById(R.id.progress_bar);
    header_error = itemView.findViewById(R.id.header_error);
    btn_retry = itemView.findViewById(R.id.btn_retry);
  }

  public void bind(LoadState loadState) {
    if (loadState instanceof LoadState.Error) {
      LoadState.Error loadStateError = (LoadState.Error) loadState;
      header_error.setText(loadStateError.getError().getLocalizedMessage());
    }
    progress_bar.setVisibility(loadState instanceof LoadState.Loading ? View.VISIBLE : View.GONE);
    btn_retry.setVisibility(loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE);

    btn_retry.setOnClickListener(retryCallback);
    header_error.setVisibility(loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE);
  }
}
