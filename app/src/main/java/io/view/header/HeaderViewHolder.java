package io.view.header;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class HeaderViewHolder extends RecyclerView.ViewHolder {

  private final HeaderViewContract headerViewContract;

  public HeaderViewHolder(View itemView) {
    super(itemView);
    this.headerViewContract = new HeaderViewContract(itemView);
  }

  public void bind(HeaderEntity entity) {
    headerViewContract.bind(entity);
  }
}
