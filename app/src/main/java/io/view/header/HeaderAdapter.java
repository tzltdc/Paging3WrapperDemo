package io.view.header;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import io.husayn.paging_library_sample.listing.ItemViewType;
import javax.inject.Inject;
import timber.log.Timber;

public class HeaderAdapter extends RecyclerView.Adapter<HeaderViewHolder> {

  private HeaderEntity headerEntity = null;

  @Inject
  public HeaderAdapter() {}

  @NonNull
  @Override
  public HeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new HeaderViewHolder(parent);
  }

  @Override
  public void onBindViewHolder(@NonNull HeaderViewHolder holder, int position) {
    if (headerEntity == null) {
      Timber.w("headerEntity is empty on BindViewHolder %s", this);
    } else {
      holder.bind(headerEntity);
    }
  }

  @Override
  public int getItemViewType(int position) {
    return ItemViewType.ITEM_VIEW_TYPE_HEADER;
  }

  @Override
  public int getItemCount() {
    return headerEntity == null ? 0 : 1;
  }

  public void bind(@Nullable HeaderEntity headerEntity) {
    this.headerEntity = headerEntity;
    notifyDataSetChanged();
  }
}
