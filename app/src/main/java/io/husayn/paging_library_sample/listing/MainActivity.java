package io.husayn.paging_library_sample.listing;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.CombinedLoadStates;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.ConcatAdapter.Config;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup;
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
import io.thread.MainScheduler;
import io.view.header.FooterAdapter;
import io.view.header.FooterEntity;
import io.view.header.HeaderAdapter;
import io.view.header.HeaderEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import kotlin.Unit;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity
    implements OnItemClickCallback, QueryCallback, MainUI {

  @Inject QueryStream queryStream;
  @Inject PagingPokemonRepo pagingPokemonRepo;
  @Inject PokemonAdapter pokemonAdapter;
  @Inject HeaderAdapter headerAdapter;
  @Inject QueryAdapter queryAdapter;
  @Inject FooterAdapter footerAdapter;
  private ConcatAdapter concatAdapter;

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
    pokemonAdapter.addLoadStateListener(this::onStateChanged);
    RecyclerView recyclerView = findViewById(R.id.rv_pokemons);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(layout());
    concatAdapter =
        new ConcatAdapter(config(), Arrays.asList(headerAdapter, pokemonAdapter, footerAdapter));
    recyclerView.setAdapter(concatAdapter);
  }

  private Config config() {
    return new Config.Builder().setIsolateViewTypes(false).build();
  }

  private GridLayoutManager layout() {
    GridLayoutManager manager = new GridLayoutManager(this, ItemViewType.SPAN_FULL);
    MySpanSizeLookup spanSizeLookup = new MySpanSizeLookup();
    spanSizeLookup.setSpanIndexCacheEnabled(true);
    manager.setSpanSizeLookup(spanSizeLookup);
    return manager;
  }

  /** */
  private void retry(View view) {
    pokemonAdapter.retry();
  }

  private void bindPagingData() {
    pagingPokemonRepo
        .rxPagingData()
        .observeOn(MainScheduler.get())
        .as(autoDisposable(AndroidLifecycleScopeProvider.from(this)))
        .subscribe(this::submitList);
  }

  private void bindQuery() {
    RecyclerView queryRecyclerView = findViewById(R.id.rv_query);
    queryRecyclerView.setLayoutManager(
        new GridLayoutManager(this, getResources().getInteger(R.integer.query_span_count)));
    queryRecyclerView.setAdapter(queryAdapter);
  }

  private void submitList(PagingData<Pokemon> pagingData) {
    Timber.i("onStateChanged submitList:%s", pagingData);
    pokemonAdapter.submitData(getLifecycle(), pagingData);
  }

  private Unit onStateChanged(CombinedLoadStates state) {
    List<Pokemon> snapshot = pokemonAdapter.snapshot().getItems();
    String summary = content(snapshot);
    HeaderEntity headerEntity =
        StateMapper.headerEntity(PagingViewModel.create(state.getRefresh(), snapshot));

    FooterEntity footerEntity =
        StateMapper.footerEntity(PagingViewModel.create(state.getAppend(), snapshot));
    Timber.i(
        "onStateChanged header:%s,footer:%s,snapshot:%s", headerEntity, footerEntity, snapshot);
    headerAdapter.bind(headerEntity);
    footerAdapter.bind(footerEntity);
    findViewById(R.id.tv_count).post(() -> updateStatus(summary));
    return Unit.INSTANCE;
  }

  private void updateStatus(String content) {
    ((TextView) findViewById(R.id.tv_count)).setText(content);
  }

  private String content(List<Pokemon> snapshot) {
    return String.format(Locale.getDefault(), "total:%d", snapshot.size());
  }

  @Override
  public void onItemClick(Pokemon pokemon) {
    Timber.i("onItemClick:%s", pokemon);
    startActivity(DetailActivity.construct(PokemonId.create(pokemon.id), this));
  }

  @Override
  public void query(String query) {
    queryStream.accept(PagingQueryMapper.map(query));
  }

  @dagger.Module
  public abstract static class Module {

    private static final int PAGE_SIZE = 2;
    private static final int PREFETCH_DISTANCE = 2;

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

  private class MySpanSizeLookup extends SpanSizeLookup {

    @Override
    public int getSpanSize(int position) {
      int itemViewType = concatAdapter.getItemViewType(position);
      return ItemViewType.map(itemViewType);
    }
  }
}
