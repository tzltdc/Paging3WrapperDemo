package paging.wrapper.di.app;

import android.app.Application;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import paging.wrapper.app.PokemonApplication;
import paging.wrapper.app.config.AppContext;

@AppScope
@Component(modules = {AppModule.class, AndroidModule.class})
public interface AppComponent extends AndroidInjector<PokemonApplication> {

  Application application();

  @Component.Factory
  interface Factory {

    AppComponent create(@BindsInstance AppContext appContext);
  }
}
