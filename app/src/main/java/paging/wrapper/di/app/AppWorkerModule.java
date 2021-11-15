package paging.wrapper.di.app;

import com.google.common.collect.ImmutableSet;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import dagger.multibindings.Multibinds;
import java.util.Set;
import paging.wrapper.app.AutoDisposeWorker;
import paging.wrapper.app.InitDatabaseWorker;

@Module
public abstract class AppWorkerModule {

  @Multibinds
  public abstract Set<AutoDisposeWorker> appWorkerSet();

  @AppScope
  @Provides
  public static ImmutableSet<AutoDisposeWorker> immutableAppWorkerSet(Set<AutoDisposeWorker> set) {
    return ImmutableSet.copyOf(set);
  }

  @Binds
  @IntoSet
  public abstract AutoDisposeWorker bind(InitDatabaseWorker databaseWorker);
}
