package androidx.paging.wrapper;

import static com.google.common.truth.Truth.assertThat;

import androidx.paging.CombinedLoadStatesData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryanharter.auto.value.gson.GenerateTypeAdapter;
import io.github.android.tang.tony.test.util.TestResourcesRule;
import org.junit.Test;

public class CombinedLoadStatesTest {

  @Test
  public void randomCouldBeParsed() {
    assertThat(CombinedLoadStatesMapper.wrap(CombinedLoadStatesData.RANDOM))
        .isEqualTo(fromJson("combined_load_states_0_random.json"));
  }

  private CombinedLoadStates fromJson(String path) {
    return gson().fromJson(new TestResourcesRule(path).content(), CombinedLoadStates.class);
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
