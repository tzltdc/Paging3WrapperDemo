package io.di.view_module;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import io.husayn.paging_library_sample.listing.MainViewModel;

@Module
public abstract class ViewModelRegisterModule {

  @Binds
  @IntoMap
  @ViewModelKey(MainViewModel.class)
  abstract ViewModel viewModel1(MainViewModel mainViewModel);
}
