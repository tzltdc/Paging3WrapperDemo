package io.husayn.paging_library_sample;

import android.app.Application;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;

@AppScope
@Component
public interface AppComponent extends AndroidInjector<AppComponent> {

  @Component.Factory
  interface Factory {

    AppComponent newMyComponent(@BindsInstance Application application);
  }
}
