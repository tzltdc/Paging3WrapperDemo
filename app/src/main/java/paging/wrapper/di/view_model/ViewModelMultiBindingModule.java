package paging.wrapper.di.view_model;

import androidx.lifecycle.ViewModel;
import dagger.Module;
import dagger.Provides;
import java.util.Map;
import javax.inject.Provider;
import paging.wrapper.di.app.AppScope;

@Module
public abstract class ViewModelMultiBindingModule {

  @AppScope
  @Provides
  static ViewModelFactory viewModelFactory(
      Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
    return new ViewModelFactory(providerMap);
  }
}
