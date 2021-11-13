package io.di.view_module;

import androidx.lifecycle.ViewModel;
import dagger.Module;
import dagger.Provides;
import io.husayn.paging_library_sample.AppScope;
import java.util.Map;
import javax.inject.Provider;

@Module
public abstract class ViewModelMultiBindingModule {

  @AppScope
  @Provides
  static ViewModelFactory viewModelFactory(
      Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
    return new ViewModelFactory(providerMap);
  }
}
