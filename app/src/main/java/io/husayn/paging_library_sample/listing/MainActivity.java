package io.husayn.paging_library_sample.listing;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.husayn.paging_library_sample.R;
import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.data.PokemonDataBase;
import io.husayn.paging_library_sample.listing.PokemonViewHolder.OnItemClickCallback;
import io.husayn.paging_library_sample.listing.QueryViewHolder.QueryCallback;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickCallback, QueryCallback {

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
    queryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    queryRecyclerView.setAdapter(new QueryAdapter(this, get()));
  }

  private List<String> get() {
    return Arrays.asList("a", "b", "c", "d", "ee", "ab", "abc");
  }

  private String getSearchKey() {
    return null;
  }

  private void submitList(PagingData<Pokemon> pokemonPagingData) {
    adapter.submitData(getLifecycle(), pokemonPagingData);
  }

  @Override
  public void onItemClick(Pokemon pokemon) {

    new DatabaseAsync().execute(pokemon);
  }

  @Override
  public void onItemClick(String query) {
    viewModel.postValue(PagingQuery.create(query));
  }

  @SuppressLint("StaticFieldLeak")
  private class DatabaseAsync extends AsyncTask<Pokemon, Void, Void> {

    @Override
    protected void onPreExecute() {
      super.onPreExecute();

      // Perform pre-adding operation here.
    }

    @Override
    protected Void doInBackground(Pokemon... pokemons) {
      Pokemon pokemon = pokemons[0];
      int id = pokemon.id;
      if (System.currentTimeMillis() % 20 != 0) {
        PokemonDataBase.getInstance(MainActivity.this).pokemonDao().deleteById(id);
      } else {
        PokemonDataBase.getInstance(MainActivity.this)
            .pokemonDao()
            .update(id, pokemon.name + " " + pokemon.name);
      }
      viewModel.postValue(PagingQuery.create(getSearchKey()));
      orderBy = !orderBy;
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
      // To after addition operation here.
    }
  }
}
