package paging.wrapper.di.app;

import dagger.Module;
import paging.wrapper.di.view_model.ViewModelMultiBindingModule;
import paging.wrapper.di.view_model.ViewModelRegisterModule;

@Module(
    includes = {
      DaggerAndroidModule.class,
      ApplicationDerivedModule.class,
      DatabaseModule.class,
      AppContextDerivedModule.class,
      ViewModelMultiBindingModule.class,
      ViewModelRegisterModule.class
    })
public abstract class AndroidModule {}
