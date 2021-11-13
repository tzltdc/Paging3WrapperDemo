package io.husayn.paging_library_sample.listing;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.CombinedLoadStates;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import dagger.Binds;
import dagger.Provides;
import dagger.android.AndroidInjection;
import io.husayn.paging_library_sample.ActivityScope;
import io.husayn.paging_library_sample.R;
import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.listing.PokemonViewHolder.OnItemClickCallback;
import io.husayn.paging_library_sample.listing.QueryViewHolder.QueryCallback;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import kotlin.Unit;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity
    implements OnItemClickCallback, QueryCallback, MainUI {

  private boolean orderBy;

  @Inject QueryStream queryStream;
  @Inject MainViewModel viewModel;
  @Inject PokemonAdapter adapter;
  @Inject QueryAdapter queryAdapter;

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
    orderBy = !orderBy;
  }

  private void bindPagingData() {
    viewModel
        .rxPagingData()
        .as(autoDisposable(AndroidLifecycleScopeProvider.from(this)))
        .subscribe(this::submitList);
  }

  private void bindQuery() {
    RecyclerView queryRecyclerView = findViewById(R.id.rv_query);
    queryRecyclerView.setLayoutManager(
        new GridLayoutManager(this, getResources().getInteger(R.integer.query_span_count)));
    queryRecyclerView.setAdapter(queryAdapter);
  }

  private void submitList(PagingData<Pokemon> pokemonPagingData) {
    adapter.submitData(getLifecycle(), pokemonPagingData);
    adapter.addLoadStateListener(this::onLoaded);
  }

  @SuppressLint("SetTextI18n")
  private Unit onLoaded(CombinedLoadStates state) {
    findViewById(R.id.tv_count).post(this::updateStatus);
    return Unit.INSTANCE;
  }

  private void updateStatus() {
    ((TextView) findViewById(R.id.tv_count)).setText(content());
  }

  private String content() {
    return String.format(Locale.getDefault(), "total:%d", adapter.getItemCount());
  }

  @Override
  public void onItemClick(Pokemon pokemon) {
    Timber.i("pokemon:%s", pokemon);
  }

  @Override
  public void query(String query) {
    queryStream.accept(PagingQueryMapper.map(query));
  }

  @dagger.Module
  public abstract static class Module {

    private static final int PAGE_SIZE = 20;
    private static final int PREFETCH_DISTANCE = 5;

    @ActivityScope
    @Provides
    public static List<String> query() {
      return FilterOptionProvider.get();
    }

    @ActivityScope
    @Provides
    public static QueryStreamImpl queryStreamImpl() {
      return new QueryStreamImpl();
    }

    @ActivityScope
    @Provides
    public static PagingConfig androidPagingConfig() {
      return new PagingConfig(PAGE_SIZE, PREFETCH_DISTANCE, true);
    }

    @Binds
    public abstract MainUI mainUI(MainActivity mainActivity);

    @Binds
    public abstract QueryStream bindQueryStream(QueryStreamImpl impl);

    @Binds
    public abstract QueryStreaming bindQueryStreaming(QueryStreamImpl impl);

    @Binds
    public abstract QueryViewHolder.QueryCallback queryViewHolderQueryCallback(
        MainActivity mainActivity);

    @Binds
    public abstract PokemonViewHolder.OnItemClickCallback pokemonViewHolderOnItemClickCallback(
        MainActivity mainActivity);
  }
}
