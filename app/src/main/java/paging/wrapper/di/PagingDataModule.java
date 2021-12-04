package paging.wrapper.di;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import paging.wrapper.data.PagerFactory;
import paging.wrapper.data.RemotePagerFactoryImpl;
import paging.wrapper.di.app.ActivityScope;
import paging.wrapper.stream.PagingDataStream;
import paging.wrapper.stream.PagingDataStreamImpl;
import paging.wrapper.stream.PagingDataStreaming;

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

  @ActivityScope
  @Binds
  public abstract PagerFactory pagerFactory(RemotePagerFactoryImpl impl);
}
