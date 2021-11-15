package paging.wrapper.di.app;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import paging.wrapper.demo.DetailActivity;
import paging.wrapper.demo.MainActivity;

@Module
public abstract class ActivityInjectorModule {

  @ActivityScope
  @ContributesAndroidInjector(modules = {MainActivity.Module.class})
  public abstract MainActivity mainActivity();

  @ActivityScope
  @ContributesAndroidInjector(modules = {DetailActivity.Module.class})
  public abstract DetailActivity detailActivity();
}
