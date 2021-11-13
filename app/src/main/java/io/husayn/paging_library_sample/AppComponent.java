package io.husayn.paging_library_sample;

import android.app.Application;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@AppScope
@Component(modules = AndroidSupportInjectionModule.class)
public interface AppComponent {

  Application application();

  void inject(PokemonApplication pokemonApplication);

  @Component.Factory
  interface Factory {

    AppComponent newMyComponent(@BindsInstance Application application);
  }
}
