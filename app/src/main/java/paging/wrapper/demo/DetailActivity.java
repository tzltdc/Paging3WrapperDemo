package paging.wrapper.demo;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import dagger.android.AndroidInjection;
import io.husayn.paging_library_sample.R;
import javax.inject.Inject;
import paging.wrapper.data.PokemonRepo;
import paging.wrapper.di.thread.MainScheduler;
import paging.wrapper.di.thread.WorkerScheduler;
import paging.wrapper.model.data.Pokemon;
import paging.wrapper.model.data.PokemonId;

public class DetailActivity extends AppCompatActivity {

  @Inject PokemonRepo pokemonRepo;
  @Inject WorkerScheduler workerScheduler;
  private TextView tv_pokemon_id;
  private TextView tv_pokemon_name;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_details);
    PokemonId pokemonId = PokemonId.create(getIntent().getIntExtra("extra_pokemon_id", 0));
    initView();
    bindData(pokemonId);
  }

  private void initView() {
    tv_pokemon_id = findViewById(R.id.tv_pokemon_id);
    tv_pokemon_name = findViewById(R.id.tv_pokemon_name);
  }

  public static Intent construct(PokemonId pokemonId, Activity activity) {
    return new Intent(activity, DetailActivity.class).putExtra("extra_pokemon_id", pokemonId.get());
  }

  private void bindData(PokemonId pokemonId) {
    pokemonRepo
        .observe(pokemonId)
        .observeOn(workerScheduler.get())
        .observeOn(MainScheduler.get())
        .as(autoDisposable(AndroidLifecycleScopeProvider.from(this)))
        .subscribe(this::bindView);
  }

  private void bindView(Pokemon pokemon) {
    tv_pokemon_id.setText(String.valueOf(pokemon.id));
    tv_pokemon_name.setText(String.valueOf(pokemon.name));
  }

  @dagger.Module
  public abstract static class Module {}
}
