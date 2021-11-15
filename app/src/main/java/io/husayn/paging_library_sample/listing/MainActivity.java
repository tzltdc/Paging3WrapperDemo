package io.husayn.paging_library_sample.listing;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.CombinedLoadStates;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import dagger.Binds;
import dagger.Provides;
import dagger.android.AndroidInjection;
import io.husayn.paging_library_sample.ActivityScope;
import io.husayn.paging_library_sample.R;
import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.listing.PokemonViewHolder.OnItemClickCallback;
import io.husayn.paging_library_sample.listing.QueryViewHolder.QueryCallback;
import io.stream.footer_entity.FooterEntityModule;
import io.stream.footer_entity.FooterModel;
import io.stream.load_state.footer.CombinedLoadStatesStream;
import io.stream.load_state.footer.FooterEntityGenerator;
import io.stream.load_state.footer.FooterLoadStateModule;
import io.stream.load_state.footer.FooterModelWorker;
import io.stream.paging.PagingDataListSnapshotProvider;
import io.stream.paging.PagingDataModule;
import io.stream.paging.PagingDataStreaming;
import io.stream.paging.PagingDataWorker;
import io.thread.MainScheduler;
import io.view.header.FooterEntity;
import io.view.header.FooterEntity.Error;
import io.view.header.HeaderEntity;
import io.view.header.HeaderEntity.Error.ErrorAction;
import io.view.header.HeaderViewContract;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import kotlin.Unit;
import timber.log.Timber;

@ActivityScope
public class MainActivity extends AppCompatActivity
    implements OnItemClickCallback, QueryCallback, MainUI, PagingDataListSnapshotProvider {

  @Inject QueryStream queryStream;
  @Inject PagingDataStreaming pagingDataStreaming;
  @Inject PagingPokemonRepo pagingPokemonRepo;
  @Inject PokemonAdapter pokemonAdapter;
  @Inject QueryAdapter queryAdapter;
  @Inject CombinedLoadStatesStream combinedLoadStatesStream;
  @Inject PagingDataWorker pagingDataWorker;
  @Inject FooterModelWorker footerModelWorker;
  @Inject FooterEntityGenerator footerEntityGenerator;
  private TextView tv_summary;
  private FrameLayout fl_header_root_view;
  private FrameLayout fl_page_data_list_root_view;
  private SwipeRefreshLayout srl_refresh;
  private HeaderViewContract headerViewContract;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initView();
    bindPagingData();
    bindQuery();
    bindRecyclerView();
    bindRefresh();
    bindWorker();
  }

  private void bindWorker() {
    pagingDataWorker.attach(AndroidLifecycleScopeProvider.from(this));
    footerEntityGenerator.attach(AndroidLifecycleScopeProvider.from(this));
    footerModelWorker.attach(AndroidLifecycleScopeProvider.from(this));
  }

  private void bindRefresh() {
    srl_refresh.setOnRefreshListener(() -> pokemonAdapter.refresh());
  }

  private void initView() {
    tv_summary = findViewById(R.id.tv_count);
    srl_refresh = findViewById(R.id.srl_refresh);
    fl_header_root_view = findViewById(R.id.fl_header_root_view);
    fl_page_data_list_root_view = findViewById(R.id.fl_page_data_list_root_view);
    headerViewContract = new HeaderViewContract(fl_header_root_view);
  }

  private void bindRecyclerView() {
    pokemonAdapter.addLoadStateListener(this::onLoadStateChanged);
    RecyclerView recyclerView = findViewById(R.id.rv_pokemons);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(layout());
    recyclerView.setAdapter(pokemonAdapter);
  }

  private GridLayoutManager layout() {
    GridLayoutManager manager = new GridLayoutManager(this, ItemViewType.SPAN_FULL);
    MySpanSizeLookup spanSizeLookup = new MySpanSizeLookup();
    spanSizeLookup.setSpanIndexCacheEnabled(true);
    manager.setSpanSizeLookup(spanSizeLookup);
    return manager;
  }

  private void bindPagingData() {
    pagingDataStreaming
        .streaming()
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
    Timber.i("onLoadStateChanged submitList:%s", pagingData);
    srl_refresh.setRefreshing(false);
    pokemonAdapter.submitData(getLifecycle(), pagingData);
  }

  private Unit onLoadStateChanged(CombinedLoadStates state) {
    bind(state);
    combinedLoadStatesStream.accept(state);
    return Unit.INSTANCE;
  }

  private void bind(CombinedLoadStates state) {
    List<Pokemon> snapshot = pokemonAdapter.snapshot().getItems();
    String summary = content(snapshot);
    HeaderEntity headerEntity =
        StateMapper.headerEntity(
            PagingViewModel.create(state.getRefresh(), snapshot), new HeaderErrorAction());

    FooterEntity footerEntity =
        StateMapper.footerEntity(
            PagingViewModel.create(state.getAppend(), snapshot),
            new FooterErrorAction(pokemonAdapter));
    Timber.i(
        "onLoadStateChanged header:%s,footer:%s,snapshot:%s", headerEntity, footerEntity, snapshot);
    updateLayer(headerEntity);
    tv_summary.setText(summary);
  }

  private void updateLayer(@Nullable HeaderEntity headerEntity) {
    if (headerEntity == null) {
      fl_header_root_view.setVisibility(View.GONE);
      fl_page_data_list_root_view.setVisibility(View.VISIBLE);
    } else {
      headerViewContract.bind(headerEntity);
      fl_header_root_view.setVisibility(View.VISIBLE);
      fl_page_data_list_root_view.setVisibility(View.GONE);
    }
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
  public void query(FilterBean query) {
    queryStream.accept(
        PagingQueryContext.create(query.description(), PagingQueryMapper.map(query.value())));
  }

  @Override
  public List<Pokemon> snapshot() {
    return pokemonAdapter.snapshot().getItems();
  }

  @Override
  public void bindFooterModel(FooterModel model) {
    pokemonAdapter.bind(model);
  }

  @dagger.Module(
      includes = {FooterLoadStateModule.class, FooterEntityModule.class, PagingDataModule.class})
  public abstract static class Module {

    private static final int PAGE_SIZE = 10;
    private static final int INITIAL_SIZE = 20;
    private static final int PREFETCH_DISTANCE = 10;

    @ActivityScope
    @Provides
    public static List<FilterBean> query() {
      return FilterOptionProvider.get();
    }

    @ActivityScope
    @Provides
    public static QueryStreamImpl queryStreamImpl() {
      return new QueryStreamImpl();
    }

    @ActivityScope
    @Binds
    public abstract Error.ErrorAction errorAction(FooterErrorAction footerErrorAction);

    @ActivityScope
    @Provides
    public static PagingConfig androidPagingConfig() {
      // Must enablePlaceholders to stop the recycler view from flinching when PagingData is
      // submitted.
      return new PagingConfig(PAGE_SIZE, PREFETCH_DISTANCE, true, INITIAL_SIZE);
    }

    @Binds
    public abstract MainUI mainUI(MainActivity mainActivity);

    @Binds
    public abstract PagingDataListSnapshotProvider pagingDataListSnapshotProvider(
        MainActivity mainActivity);

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

  private class HeaderErrorAction extends ErrorAction {

    @Override
    public String text() {
      return "Reload";
    }

    @Override
    public Callback callback() {
      return error -> MainActivity.this.pokemonAdapter.retry();
    }
  }

  private class MySpanSizeLookup extends SpanSizeLookup {

    @Override
    public int getSpanSize(int position) {
      int itemViewType = pokemonAdapter.getItemViewType(position);
      return ItemViewType.map(itemViewType);
    }
  }

  public static class FooterErrorAction extends Error.ErrorAction {

    private final PokemonAdapter pokemonAdapter;

    @Inject
    public FooterErrorAction(PokemonAdapter pokemonAdapter) {
      Timber.i("FooterErrorAction PokemonAdapter:%s", pokemonAdapter);
      this.pokemonAdapter = pokemonAdapter;
    }

    @Override
    public String text() {
      return "Retry";
    }

    @Override
    public Callback callback() {
      return error -> executeRetry();
    }

    private void executeRetry() {
      Timber.i("Retry to fetch the data");
      pokemonAdapter.retry();
    }
  }
}
