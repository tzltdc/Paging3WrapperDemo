package paging.wrapper;

import dagger.BindsInstance;
import dagger.Component;
import paging.wrapper.data.RemotePokenmonPagingSourceTest;
import paging.wrapper.db.ServerDto;
import paging.wrapper.di.app.AppScope;
import paging.wrapper.di.app.JavaModule;

@AppScope
@Component(modules = {JavaModule.class, TestModule.class})
public interface TestComponent {

  void inject(OffsetHelperTest instance);

  void inject(RemotePokenmonPagingSourceTest instance);

  void inject(PokemonBackendServiceTest instance);

  @Component.Factory
  interface Factory {

    TestComponent create(@BindsInstance ServerDto serverDto);
  }
}
