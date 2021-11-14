package io.stream.load_state.footer;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.husayn.paging_library_sample.ActivityScope;

@Module
public abstract class FooterLoadStateModule {

  @ActivityScope
  @Provides
  public static FooterLoadStateStreamImpl footerLoadStateStreamImpl() {
    return new FooterLoadStateStreamImpl();
  }

  @ActivityScope
  @Binds
  public abstract FooterLoadStateStream footerLoadStateStream(FooterLoadStateStreamImpl impl);

  @ActivityScope
  @Binds
  public abstract FooterLoadStateStreaming footerLoadStateStreaming(FooterLoadStateStreamImpl impl);
}
