package io.husayn.paging_library_sample.data;

import android.app.Application;
import dagger.BindsInstance;
import dagger.Component;
import io.husayn.paging_library_sample.AppModule;
import io.husayn.paging_library_sample.AppScope;

@AppScope
@Component(modules = {AppModule.class})
public interface TestAppComponent {

  void inject(PokemonDataBaseShould pokemonDataBaseShould);

  @Component.Factory
  interface Factory {

    TestAppComponent create(@BindsInstance Application application);
  }
}
