package io.stream.footer_entity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.husayn.paging_library_sample.ActivityScope;

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
