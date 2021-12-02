package paging.wrapper;

import dagger.BindsInstance;
import dagger.Component;
import paging.wrapper.db.ServerDto;
import paging.wrapper.di.app.AppScope;
import paging.wrapper.di.app.JavaModule;

@AppScope
@Component(modules = {JavaModule.class})
public interface TestComponent {

  public void inject(OffsetHelperTest instance);

  public void inject(PokemonBackendServiceTest instance);

  @Component.Factory
  interface Factory {

    TestComponent create(@BindsInstance ServerDto serverDto);
  }
}
