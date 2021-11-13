package io.husayn.paging_library_sample.listing;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;

// Adapter that displays a loading spinner when
// state is LoadState.Loading, and an error message and retry
// button when state is LoadState.Error.
public class ExampleLoadStateAdapter extends LoadStateAdapter<LoadStateViewHolder> {

  private final View.OnClickListener retryCallback;

  public ExampleLoadStateAdapter(View.OnClickListener retryCallback) {
    this.retryCallback = retryCallback;
  }

  @NonNull
  @Override
  public LoadStateViewHolder onCreateViewHolder(
      @NonNull ViewGroup parent, @NonNull LoadState loadState) {
    return new LoadStateViewHolder(parent, retryCallback);
  }

  @Override
  public void onBindViewHolder(@NonNull LoadStateViewHolder holder, @NonNull LoadState loadState) {
    holder.bind(loadState);
  }
}
