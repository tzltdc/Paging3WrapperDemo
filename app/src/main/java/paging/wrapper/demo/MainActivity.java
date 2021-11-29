package paging.wrapper.demo;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.test.espresso.IdlingResource;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import dagger.Binds;
import dagger.Provides;
import dagger.android.AndroidInjection;
import io.husayn.paging_library_sample.R;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import paging.wrapper.contract.ClickActionContract;
import paging.wrapper.contract.HeaderContract;
import paging.wrapper.contract.ItemViewType;
import paging.wrapper.contract.PagingAdapterContract;
import paging.wrapper.contract.impl.HeaderViewContract;
import paging.wrapper.demo.PokemonViewHolder.OnItemClickCallback;
import paging.wrapper.demo.ui.query.FeatureQueryModule;
import paging.wrapper.demo.ui.query.QueryAdapter;
import paging.wrapper.demo.ui.query.QueryStream;
import paging.wrapper.demo.ui.query.QueryViewHolder.QueryCallback;
import paging.wrapper.di.ContractModule;
import paging.wrapper.di.FooterEntityModule;
import paging.wrapper.di.PagingDataModule;
import paging.wrapper.di.app.ActivityScope;
import paging.wrapper.di.thread.MainScheduler;
import paging.wrapper.model.data.FilterBean;
import paging.wrapper.model.data.Pokemon;
import paging.wrapper.model.data.PokemonId;
import paging.wrapper.model.ui.HeaderEntity;
import paging.wrapper.stream.CombinedLoadStatesCallback;
import paging.wrapper.stream.LoadStateStreaming;
import paging.wrapper.stream.PagingDataStreaming;
import paging.wrapper.test.AppIdleStateConsumerWorker;
import paging.wrapper.test.AppIdleStateSourceWorker;
import paging.wrapper.test.AppIdleStateStreamModule;
import paging.wrapper.worker.FooterEntityGenerator;
import paging.wrapper.worker.FooterModelWorker;
import paging.wrapper.worker.HeaderEntityWorker;
import paging.wrapper.worker.PagingDataWorker;
import timber.log.Timber;

@ActivityScope
public class MainActivity extends AppCompatActivity
    implements OnItemClickCallback, QueryCallback, MainUI, HeaderContract {

  @Inject QueryStream queryStream;
  @Inject PagingDataStreaming pagingDataStreaming;
  @Inject PokemonAdapter pokemonAdapter;
  @Inject CombinedLoadStatesCallback combinedLoadStatesCallback;
  @Inject QueryAdapter queryAdapter;
  @Inject PagingDataWorker pagingDataWorker;
  @Inject AppIdleStateSourceWorker appIdleStateSourceWorker;
  @Inject FooterModelWorker footerModelWorker;
  @Inject HeaderEntityWorker headerEntityWorker;
  @Inject FooterEntityGenerator footerEntityGenerator;
  @Inject ClickActionContract clickActionContract;
  @Inject AppIdleStateConsumerWorker appIdleStateConsumerWorker;
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
    appIdleStateConsumerWorker.attach(AndroidLifecycleScopeProvider.from(this));
    appIdleStateSourceWorker.attach(AndroidLifecycleScopeProvider.from(this));
    pagingDataWorker.attach(AndroidLifecycleScopeProvider.from(this));
    footerEntityGenerator.attach(AndroidLifecycleScopeProvider.from(this));
    footerModelWorker.attach(AndroidLifecycleScopeProvider.from(this));
    headerEntityWorker.attach(AndroidLifecycleScopeProvider.from(this));
  }

  private void bindRefresh() {
    srl_refresh.setOnRefreshListener(() -> pokemonAdapter.refresh());
  }

  private void initView() {
    tv_summary = findViewById(R.id.tv_count);
    srl_refresh = findViewById(R.id.srl_refresh);
    fl_header_root_view = findViewById(R.id.fl_header_root_view);
    fl_page_data_list_root_view = findViewById(R.id.fl_page_data_list_root_view);
    headerViewContract = new HeaderViewContract(fl_header_root_view, clickActionContract);
  }

  private void bindRecyclerView() {
    pokemonAdapter.addLoadStateListener(combinedLoadStatesCallback);
    observeSummary();
    RecyclerView recyclerView = findViewById(R.id.rv_pokemons);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(layout());
    recyclerView.setAdapter(pokemonAdapter);
  }

  private void observeSummary() {
    // TODO: 11/14/21 This is not working
    pokemonAdapter.registerAdapterDataObserver(
        new AdapterDataObserver() {
          @Override
          public void onChanged() {
            super.onChanged();
            tv_summary.setText(content(pokemonAdapter.snapshot().getItems()));
          }
        });
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
    Timber.v("onLoadStateChanged:");
    Timber.v("onLoadStateChanged:");
    Timber.v("onLoadStateChanged:");
    Timber.i("onLoadStateChanged:submitList:%s", pagingData);
    srl_refresh.setRefreshing(false);
    pokemonAdapter.submitData(getLifecycle(), pagingData);
  }

  public void bind(@Nullable HeaderEntity headerEntity) {
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

  @SuppressLint("NotifyDataSetChanged")
  @Override
  public void query(FilterBean bean) {
    queryStream.accept(bean);
    queryAdapter.notifyDataSetChanged();
  }

  public IdlingResource pagingIdlingResource() {
    return appIdleStateConsumerWorker;
  }

  @dagger.Module(
      includes = {
        AppIdleStateStreamModule.class,
        ContractModule.class,
        FeatureQueryModule.class,
        FooterEntityModule.class,
        PagingDataModule.class
      })
  public abstract static class Module {

    private static final int PAGE_SIZE = 10;
    private static final int INITIAL_SIZE = 20;
    private static final int PREFETCH_DISTANCE = 10;

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
    public abstract LoadStateStreaming loadStateStreaming(
        CombinedLoadStatesCallback combinedLoadStatesCallback);

    @Binds
    public abstract HeaderContract headerContract(MainActivity mainActivity);

    @Binds
    public abstract PagingAdapterContract bindPokemonAdapterCallback(PokemonAdapter impl);

    @Binds
    public abstract PokemonViewHolder.OnItemClickCallback pokemonViewHolderOnItemClickCallback(
        MainActivity mainActivity);
  }

  private class MySpanSizeLookup extends SpanSizeLookup {

    @Override
    public int getSpanSize(int position) {
      int itemViewType = pokemonAdapter.getItemViewType(position);
      return ItemViewType.map(itemViewType);
    }
  }
}
