package io.view.header;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import io.paging.footer.ClickActionContract;

public class FooterViewHolder extends RecyclerView.ViewHolder {

  private final FooterViewContract footerViewContract;

  public FooterViewHolder(View itemView, ClickActionContract actionContract) {
    super(itemView);
    footerViewContract = new FooterViewContract(itemView, actionContract);
  }

  public void bind(FooterEntity entity) {
    footerViewContract.bind(entity);
  }
}
