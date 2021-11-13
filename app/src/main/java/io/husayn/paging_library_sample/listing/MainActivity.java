package io.husayn.paging_library_sample.listing;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.CombinedLoadStates;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import dagger.Binds;
import dagger.android.AndroidInjection;
import io.husayn.paging_library_sample.R;
import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.listing.PokemonViewHolder.OnItemClickCallback;
import io.husayn.paging_library_sample.listing.QueryViewHolder.QueryCallback;
import java.util.Locale;
import javax.inject.Inject;
import kotlin.Unit;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity
    implements OnItemClickCallback, QueryCallback, MainUI {

  private boolean orderBy;
  private TextView tv_count;

  @Inject MainViewModel viewModel;
  @Inject PokemonAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    bindPagingData();
    bindQuery();
    bindRecyclerView();
  }

  private void bindRecyclerView() {
    RecyclerView recyclerView = findViewById(R.id.rv_pokemons);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(
        new GridLayoutManager(this, getResources().getInteger(R.integer.span_count)));
    recyclerView.setAdapter(adapter);
    viewModel.postValue(PagingQuery.create(getSearchKey()));
    orderBy = !orderBy;
  }

  private void bindPagingData() {
    viewModel
        .rxPagingData()
        .as(autoDisposable(AndroidLifecycleScopeProvider.from(this)))
        .subscribe(this::submitList);
  }

  private void bindQuery() {
    tv_count = findViewById(R.id.tv_count);
    RecyclerView queryRecyclerView = findViewById(R.id.rv_query);
    queryRecyclerView.setLayoutManager(new GridLayoutManager(this, 12));
    queryRecyclerView.setAdapter(new QueryAdapter(this, FilterOptionProvider.get()));
  }

  private String getSearchKey() {
    return null;
  }

  private void submitList(PagingData<Pokemon> pokemonPagingData) {
    adapter.submitData(getLifecycle(), pokemonPagingData);
    adapter.addLoadStateListener(this::onLoaded);
  }

  @SuppressLint("SetTextI18n")
  private Unit onLoaded(CombinedLoadStates state) {
    tv_count.post(this::updateStatus);
    return Unit.INSTANCE;
  }

  private void updateStatus() {
    tv_count.setText(String.format(Locale.getDefault(), "total:%d", adapter.getItemCount()));
  }

  @Override
  public void onItemClick(Pokemon pokemon) {
    Timber.i("pokemon:%s", pokemon);
  }

  @Override
  public void query(String query) {
    viewModel.postValue(
        PagingQuery.create(query.equals(FilterOptionProvider.EMPTY) ? null : query));
  }

  @dagger.Module
  public abstract static class Module {

    @Binds
    public abstract MainUI mainUI(MainActivity mainActivity);

    @Binds
    public abstract PokemonViewHolder.OnItemClickCallback pokemonViewHolderOnItemClickCallback(
        MainActivity mainActivity);
  }
}
