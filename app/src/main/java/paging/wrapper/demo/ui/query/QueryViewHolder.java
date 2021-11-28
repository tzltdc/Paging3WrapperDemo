package paging.wrapper.demo.ui.query;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.husayn.paging_library_sample.R;
import paging.wrapper.model.data.FilterBean;

public class QueryViewHolder extends RecyclerView.ViewHolder {

  private final TextView tv_query;
  private final QueryStreaming queryStreaming;
  private final QueryCallback queryCallback;

  @AssistedInject
  public QueryViewHolder(
      @Assisted View itemView, QueryStreaming queryStreaming, QueryCallback queryCallback) {
    super(itemView);
    this.queryStreaming = queryStreaming;
    this.queryCallback = queryCallback;
    this.tv_query = itemView.findViewById(R.id.tv_query);
  }

  public void bindTo(FilterBean query) {
    itemView.setTag(query);
    tv_query.setText(query.description());
    tv_query.setTextColor(colorInt(colorRes(query.equals(queryStreaming.peek()))));
    itemView.setOnClickListener(v -> queryCallback.query(query));
  }

  @ColorInt
  private int colorInt(@ColorRes int id) {
    return ContextCompat.getColor(itemView.getContext(), id);
  }

  @ColorRes
  private int colorRes(boolean selected) {
    return selected ? R.color.colorAccent : android.R.color.black;
  }

  public interface QueryCallback {

    void query(FilterBean query);
  }
}
