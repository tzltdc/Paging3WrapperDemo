package io.husayn.paging_library_sample;

import dagger.Module;
import dagger.android.support.AndroidSupportInjectionModule;

@Module(includes = {AndroidSupportInjectionModule.class, ActivityInjectorModule.class})
public abstract class AndroidModule {}
