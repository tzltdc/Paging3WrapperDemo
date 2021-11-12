package io.husayn.paging_library_sample.listing;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.husayn.paging_library_sample.R;
import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.listing.PokemonViewHolder.OnItemClickCallback;
import io.husayn.paging_library_sample.listing.QueryViewHolder.QueryCallback;
import java.util.Arrays;
import java.util.List;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements OnItemClickCallback, QueryCallback {

  public static final String EMPTY = "Empty";
  private MainViewModel viewModel;
  private boolean orderBy;
  private PokemonAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // test google java format.
    viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    adapter = new PokemonAdapter(this);
    viewModel
        .rxPagingData()
        .as(autoDisposable(AndroidLifecycleScopeProvider.from(this)))
        .subscribe(this::submitList);

    bindQuery();
    RecyclerView recyclerView = findViewById(R.id.rv_pokemons);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(
        new GridLayoutManager(this, getResources().getInteger(R.integer.span_count)));
    recyclerView.setAdapter(adapter);
    viewModel.postValue(PagingQuery.create(getSearchKey()));
    orderBy = !orderBy;
  }

  private void bindQuery() {
    RecyclerView queryRecyclerView = findViewById(R.id.rv_query);
    queryRecyclerView.setLayoutManager(new GridLayoutManager(this, 12));
    queryRecyclerView.setAdapter(new QueryAdapter(this, get()));
  }

  private List<String> get() {
    return Arrays.asList(EMPTY, "a", "b", "ee", "abc");
  }

  private String getSearchKey() {
    return null;
  }

  private void submitList(PagingData<Pokemon> pokemonPagingData) {
    adapter.submitData(getLifecycle(), pokemonPagingData);
  }

  @Override
  public void onItemClick(Pokemon pokemon) {
    Timber.i("pokemon:%s", pokemon);
  }

  @Override
  public void query(String query) {
    viewModel.postValue(PagingQuery.create(query.equals(EMPTY) ? null : query));
  }
}
