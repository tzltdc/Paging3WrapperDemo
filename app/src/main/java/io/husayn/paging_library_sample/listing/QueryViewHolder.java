package io.husayn.paging_library_sample.listing;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import io.husayn.paging_library_sample.R;

class QueryViewHolder extends RecyclerView.ViewHolder {

  private final TextView tv_query;

  QueryViewHolder(View itemView, QueryCallback queryCallback) {
    super(itemView);
    this.queryCallback = queryCallback;
    this.tv_query = itemView.findViewById(R.id.tv_query);
  }

  void bindTo(String query) {
    itemView.setTag(query);
    tv_query.setText(query);
    itemView.setOnClickListener(v -> queryCallback.onItemClick(query));
  }

  private QueryCallback queryCallback;

  public interface QueryCallback {

    void onItemClick(String query);
  }
}
