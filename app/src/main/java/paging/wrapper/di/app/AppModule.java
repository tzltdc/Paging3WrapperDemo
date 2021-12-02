package paging.wrapper.di.app;

import dagger.Module;

@Module(includes = {JavaModule.class, AndroidModule.class})
public abstract class AppModule {}
