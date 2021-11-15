package paging.wrapper.di;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import paging.wrapper.di.app.ActivityScope;
import paging.wrapper.stream.FooterEntityStream;
import paging.wrapper.stream.FooterEntityStreamImpl;
import paging.wrapper.stream.FooterEntityStreaming;

@Module
public abstract class FooterEntityModule {

  @ActivityScope
  @Provides
  public static FooterEntityStreamImpl footerEntityStreamImpl() {
    return new FooterEntityStreamImpl();
  }

  @ActivityScope
  @Binds
  public abstract FooterEntityStream footerEntityStream(FooterEntityStreamImpl impl);

  @ActivityScope
  @Binds
  public abstract FooterEntityStreaming footerEntityStreaming(FooterEntityStreamImpl impl);
}
