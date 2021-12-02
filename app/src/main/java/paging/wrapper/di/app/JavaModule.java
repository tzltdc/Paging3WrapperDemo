package paging.wrapper.di.app;

import dagger.Module;
import paging.wrapper.di.thread.WorkerThreadModule;

@Module(
    includes = {
      AppWorkerModule.class,
      RepoModule.class,
      WorkerThreadModule.class,
    })
public abstract class JavaModule {}
