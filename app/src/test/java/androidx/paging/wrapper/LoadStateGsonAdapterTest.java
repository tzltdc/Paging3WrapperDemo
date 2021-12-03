package androidx.paging.wrapper;

import static com.google.common.truth.Truth.assertThat;

import androidx.paging.wrapper.LoadState.LoadError;
import androidx.paging.wrapper.LoadState.Loading;
import androidx.paging.wrapper.LoadState.NotLoading;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryanharter.auto.value.gson.GenerateTypeAdapter;
import io.github.android.tang.tony.test.util.TestResourcesRule;
import org.junit.Test;

public class LoadStateGsonAdapterTest {

  @Test
  public void case_1_notLoading_couldBeParsed() {
    LoadState raw = LoadState.ofNotLoading(NotLoading.COMPLETE);
    String json = gson().toJson(raw);
    assertThat(gson().fromJson(json, LoadState.class)).isEqualTo(raw);
  }

  @Test
  public void case_2_loading_couldBeParsed() {
    LoadState raw = LoadState.ofLoading(Loading.create(false));
    assertThat(gson().fromJson(gson().toJson(raw), LoadState.class)).isEqualTo(raw);
  }

  @Test
  public void case_3_1_LoadError_couldBeParsed() {
    LoadState raw = LoadState.ofError(LoadError.create("error message", false));
    assertThat(gson().fromJson(gson().toJson(raw), LoadState.class)).isEqualTo(raw);
  }

  @Test
  public void case_3_2_LoadError_couldBeParsed() {
    LoadState raw = LoadState.ofError(LoadError.create("error message", false));
    assertThat(gson().toJson(raw)).isEqualTo(new TestResourcesRule("error.json").content());
  }

  private static Gson gson() {
    return new GsonBuilder()
        .registerTypeAdapterFactory(GenerateTypeAdapter.FACTORY)
        .registerTypeHierarchyAdapter(LoadState.class, new LoadStateGsonAdapter(baseGson()))
        .create();
  }

  private static Gson baseGson() {
    return new GsonBuilder().registerTypeAdapterFactory(GenerateTypeAdapter.FACTORY).create();
  }
}
