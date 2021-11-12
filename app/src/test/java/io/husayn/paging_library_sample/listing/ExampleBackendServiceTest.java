package io.husayn.paging_library_sample.listing;

import static com.google.common.truth.Truth.assertThat;

import androidx.annotation.Nullable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ExampleBackendServiceTest {

  @Test
  public void initial_queryWithoutFilter_shouldReturn10() {
    assertThat(ExampleBackendService.query(firstRequest(null)).test().values().get(0).list())
        .hasSize(151);
  }

  @Test
  public void initial_queryWithFilter_shouldReturn10() {
    assertThat(ExampleBackendService.query(firstRequest("a")).test().values().get(0).list())
        .hasSize(83);
  }

  @Test
  public void initial_queryWithDoubleFilter_shouldReturn10() {
    assertThat(ExampleBackendService.query(firstRequest("aa")).test().values().get(0).list())
        .hasSize(0);
  }

  @Test
  public void initial_queryWithAb_shouldReturn10() {
    assertThat(ExampleBackendService.query(firstRequest("ab")).test().values().get(0).list())
        .hasSize(6);
  }

  private static PagingRequest firstRequest(@Nullable String searchKey) {
    return PagingRequest.create(0, PagingQuery.create(searchKey), PagingQueryConfig.MAX_CONFIG);
  }
}
