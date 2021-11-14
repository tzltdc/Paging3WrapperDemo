package io.stream.paging;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.husayn.paging_library_sample.ActivityScope;

@Module
public abstract class PagingDataModule {

  @ActivityScope
  @Provides
  public static PagingDataStreamImpl footerLoadStateStreamImpl() {
    return new PagingDataStreamImpl();
  }

  @ActivityScope
  @Binds
  public abstract PagingDataStream footerLoadStateStream(PagingDataStreamImpl impl);

  @ActivityScope
  @Binds
  public abstract PagingDataStreaming footerLoadStateStreaming(PagingDataStreamImpl impl);
}
