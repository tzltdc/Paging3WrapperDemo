package io.husayn.paging_library_sample;

import dagger.Module;
import io.di.view_module.ViewModelMultiBindingModule;
import io.di.view_module.ViewModelRegisterModule;

@Module(
    includes = {
      ApplicationDerivedModule.class,
      AppWorkerModule.class,
      RepoModule.class,
      AppContextDerivedModule.class,
      ViewModelMultiBindingModule.class,
      ViewModelRegisterModule.class
    })
public abstract class AppModule {}
