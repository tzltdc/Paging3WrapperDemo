package paging.wrapper.test;

import androidx.test.espresso.IdlingResource;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoSet;
import paging.wrapper.app.AutoDisposeWorker;
import paging.wrapper.di.app.AppScope;

@Module(includes = AppIdleStateStreamModule.class)
public abstract class AppIdleStateConsumerModule {

  @Binds
  @IntoSet
  public abstract AutoDisposeWorker bind(AppIdleStateConsumerWorker worker);

  @AppScope
  @Binds
  public abstract IdlingResource idlingResource(AppIdleStateConsumerWorker worker);
}
