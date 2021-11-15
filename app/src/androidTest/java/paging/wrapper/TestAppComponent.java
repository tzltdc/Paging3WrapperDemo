package paging.wrapper;

import dagger.BindsInstance;
import dagger.Component;
import paging.wrapper.app.config.AppContext;
import paging.wrapper.di.app.AppModule;
import paging.wrapper.di.app.AppScope;

@AppScope
@Component(modules = {AppModule.class})
public interface TestAppComponent {

  void inject(PokemonDataBaseShould pokemonDataBaseShould);

  @Component.Factory
  interface Factory {

    TestAppComponent create(@BindsInstance AppContext appContext);
  }
}
