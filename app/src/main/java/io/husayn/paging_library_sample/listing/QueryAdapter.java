package io.husayn.paging_library_sample.listing;

import static io.husayn.paging_library_sample.PokemonApplication.getContext;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.husayn.paging_library_sample.R;
import io.husayn.paging_library_sample.listing.QueryViewHolder.QueryCallback;
import java.util.List;

public class QueryAdapter extends RecyclerView.Adapter<QueryViewHolder> {

  private final QueryViewHolder.QueryCallback onItemClickCallback;
  private final List<String> queryList;

  public QueryAdapter(QueryCallback onItemClickCallback, List<String> queryList) {
    this.onItemClickCallback = onItemClickCallback;
    this.queryList = queryList;
  }

  @NonNull
  @Override
  public QueryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_query, parent, false);
    return new QueryViewHolder(itemView, onItemClickCallback);
  }

  @Override
  public void onBindViewHolder(@NonNull QueryViewHolder holder, int position) {
    String query = queryList.get(position);
    holder.bindTo(query);
  }

  @Override
  public int getItemCount() {
    return queryList.size();
  }
}
