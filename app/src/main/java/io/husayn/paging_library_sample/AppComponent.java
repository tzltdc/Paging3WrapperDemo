package io.husayn.paging_library_sample;

import android.app.Application;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import io.di.view_module.ViewModelMultiBindingModule;
import io.di.view_module.ViewModelRegisterModule;

@AppScope
@Component(
    modules = {
      AndroidSupportInjectionModule.class,
      ApplicationDerivedModule.class,
      AppWorkerModule.class,
      RepoModule.class,
      ViewModelMultiBindingModule.class,
      ViewModelRegisterModule.class,
      ActivityInjectorModule.class
    })
public interface AppComponent extends AndroidInjector<PokemonApplication> {

  Application application();

  @Component.Factory
  interface Factory {

    AppComponent newMyComponent(@BindsInstance Application application);
  }
}
