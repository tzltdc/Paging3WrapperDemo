package io.husayn.paging_library_sample;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.husayn.paging_library_sample.listing.MainActivity;

@Module
public abstract class ActivityInjectorModule {

  @ActivityScope
  @ContributesAndroidInjector(modules = {MainActivity.Module.class})
  public abstract MainActivity mainActivity();
}
