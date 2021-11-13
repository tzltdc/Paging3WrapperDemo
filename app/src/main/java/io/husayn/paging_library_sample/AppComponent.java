package io.husayn.paging_library_sample;

import android.app.Application;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@AppScope
@Component(
    modules = {AndroidSupportInjectionModule.class, RepoModule.class, ActivityInjectorModule.class})
public interface AppComponent extends AndroidInjector<PokemonApplication> {

  Application application();

  @Component.Factory
  interface Factory {

    AppComponent newMyComponent(@BindsInstance Application application);
  }
}
