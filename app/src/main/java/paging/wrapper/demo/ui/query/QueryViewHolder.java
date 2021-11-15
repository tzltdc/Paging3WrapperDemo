package paging.wrapper.demo.ui.query;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.husayn.paging_library_sample.R;
import paging.wrapper.model.data.FilterBean;

public class QueryViewHolder extends RecyclerView.ViewHolder {

  private final TextView tv_query;
  private final QueryCallback queryCallback;

  @AssistedInject
  public QueryViewHolder(@Assisted View itemView, QueryCallback queryCallback) {
    super(itemView);
    this.queryCallback = queryCallback;
    this.tv_query = itemView.findViewById(R.id.tv_query);
  }

  public void bindTo(FilterBean query) {
    itemView.setTag(query);
    tv_query.setText(query.description());
    itemView.setOnClickListener(v -> queryCallback.query(query));
  }

  public interface QueryCallback {

    void query(FilterBean query);
  }
}
