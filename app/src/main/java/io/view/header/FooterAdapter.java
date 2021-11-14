package io.view.header;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.husayn.paging_library_sample.listing.ItemViewType;
import javax.inject.Inject;
import timber.log.Timber;

public class FooterAdapter extends RecyclerView.Adapter<FooterViewHolder> {

  private FooterEntity footerEntity = null;

  @Inject
  public FooterAdapter() {}

  @NonNull
  @Override
  public FooterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new FooterViewHolder(parent);
  }

  @Override
  public void onBindViewHolder(@NonNull FooterViewHolder holder, int position) {
    if (footerEntity != null) {
      holder.bind(footerEntity);
    } else {
      Timber.w("footerEntity is empty on BindViewHolder %s", this);
    }
  }

  @Override
  public int getItemViewType(int position) {
    return ItemViewType.ITEM_VIEW_TYPE_FOOTER;
  }

  @Override
  public int getItemCount() {
    return footerEntity == null ? 0 : 1;
  }

  public void bind(FooterEntity footerEntity) {
    this.footerEntity = footerEntity;
    notifyDataSetChanged();
  }
}
