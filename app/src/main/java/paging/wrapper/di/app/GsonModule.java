package paging.wrapper.di.app;

import static com.ryanharter.auto.value.gson.GenerateTypeAdapter.FACTORY;

import androidx.annotation.NonNull;
import androidx.pagingx.LoadState;
import androidx.pagingx.LoadStateGsonAdapter;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import io.husayn.paging_library_sample.BuildConfig;

@Module
public abstract class GsonModule {

  @Provides
  @AppScope
  static AutoValueGson autoValueGson() {
    return new AutoValueGson(create().create());
  }

  @NonNull
  private static GsonBuilder create() {
    GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapterFactory(FACTORY);
    return BuildConfig.DEBUG ? gsonBuilder.setPrettyPrinting() : gsonBuilder;
  }

  @Provides
  @AppScope
  static AppGson appGson(AutoValueGson autoValueGson) {
    return new AppGson(
        create()
            .registerTypeHierarchyAdapter(
                LoadState.class, new LoadStateGsonAdapter(autoValueGson.get()))
            .create());
  }
}
