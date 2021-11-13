package io.husayn.paging_library_sample;

import android.app.Application;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import io.app.config.AppContext;

@AppScope
@Component(modules = {AppModule.class, AndroidModule.class})
public interface AppComponent extends AndroidInjector<PokemonApplication> {

  Application application();

  @Component.Factory
  interface Factory {

    AppComponent create(@BindsInstance AppContext appContext);
  }
}
