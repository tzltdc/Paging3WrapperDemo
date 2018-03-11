package io.husayn.paging_library_sample.listing;


import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import io.husayn.paging_library_sample.R;
import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.data.PokemonDataBase;

public class MainActivity extends AppCompatActivity implements PokemonViewHolder.OnItemClickCallback {

    private MainViewModel viewModel;
    private boolean orderBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        final PokemonAdapter adapter = new PokemonAdapter(this);
        viewModel.pokemonList.observe(this, adapter::submitList);

        RecyclerView recyclerView = findViewById(R.id.rv_pokemons);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.span_count)));
        recyclerView.setAdapter(adapter);
        viewModel.postValue(orderBy);
        orderBy = !orderBy;

    }

    @Override
    public void onItemClick(Pokemon pokemon) {


        new DatabaseAsync().execute(pokemon);


    }


    @SuppressLint("StaticFieldLeak")
    private class DatabaseAsync extends AsyncTask<Pokemon, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Perform pre-adding operation here.
        }

        @Override
        protected Void doInBackground(Pokemon... pokemons) {
            Pokemon pokemon = pokemons[0];
            int id = pokemon.id;
            if (System.currentTimeMillis() % 20 != 0) {
                PokemonDataBase.getInstance(MainActivity.this).pokemonDao().deleteById(id);
            } else {
                PokemonDataBase.getInstance(MainActivity.this).pokemonDao().update(id, pokemon.name + " " + pokemon.name);
            }
            viewModel.postValue(MainActivity.this.orderBy);
            orderBy = !orderBy;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //To after addition operation here.
        }
    }

}
