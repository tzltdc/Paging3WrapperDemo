package io.husayn.paging_library_sample.listing;

import static io.husayn.paging_library_sample.PokemonApplication.getContext;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.husayn.paging_library_sample.R;
import java.util.List;
import javax.inject.Inject;

public class QueryAdapter extends RecyclerView.Adapter<QueryViewHolder> {

  private final QueryViewHolderFactory queryViewHolderFactory;
  private final List<FilterBean> queryList;

  @Inject
  public QueryAdapter(QueryViewHolderFactory queryViewHolderFactory, List<FilterBean> queryList) {
    this.queryViewHolderFactory = queryViewHolderFactory;
    this.queryList = queryList;
  }

  @NonNull
  @Override
  public QueryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_query, parent, false);
    return queryViewHolderFactory.create(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull QueryViewHolder holder, int position) {
    FilterBean query = queryList.get(position);
    holder.bindTo(query);
  }

  @Override
  public int getItemCount() {
    return queryList.size();
  }
}
