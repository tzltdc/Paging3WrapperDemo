package paging.wrapper.di.app;

import dagger.Module;
import paging.wrapper.di.thread.WorkerThreadModule;
import paging.wrapper.di.view_model.ViewModelMultiBindingModule;
import paging.wrapper.di.view_model.ViewModelRegisterModule;

@Module(
    includes = {
      ApplicationDerivedModule.class,
      AppWorkerModule.class,
      RepoModule.class,
      WorkerThreadModule.class,
      AppContextDerivedModule.class,
      ViewModelMultiBindingModule.class,
      ViewModelRegisterModule.class
    })
public abstract class AppModule {}
