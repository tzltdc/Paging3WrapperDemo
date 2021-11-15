package io.stream.load_state.footer;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.husayn.paging_library_sample.ActivityScope;

@Module
public abstract class FooterLoadStateModule {

  @ActivityScope
  @Provides
  public static CombinedLoadStatesStreamImpl footerLoadStateStreamImpl() {
    return new CombinedLoadStatesStreamImpl();
  }

  @ActivityScope
  @Binds
  public abstract CombinedLoadStatesStream footerLoadStateStream(CombinedLoadStatesStreamImpl impl);

  @ActivityScope
  @Binds
  public abstract LoadStateStreaming footerLoadStateStreaming(CombinedLoadStatesStreamImpl impl);
}
