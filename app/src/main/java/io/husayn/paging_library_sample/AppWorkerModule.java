package io.husayn.paging_library_sample;

import com.uber.rib.core.Worker;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.Multibinds;
import java.util.Set;

@Module
public abstract class AppWorkerModule {

  @Multibinds
  public abstract Set<Worker> appWorkerSet();

  @Binds
  public abstract Worker bind(InitDatabaseWorker databaseWorker);
}
