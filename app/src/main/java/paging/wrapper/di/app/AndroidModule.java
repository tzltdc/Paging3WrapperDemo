package paging.wrapper.di.app;

import dagger.Module;
import dagger.android.support.AndroidSupportInjectionModule;

@Module(includes = {AndroidSupportInjectionModule.class, ActivityInjectorModule.class})
public abstract class AndroidModule {}
