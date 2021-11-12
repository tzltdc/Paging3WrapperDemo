package io.husayn.paging_library_sample.listing;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ExampleBackendServiceTest {

  @Test
  public void initial_queryWithoutFilter_shouldReturn10() {
    assertThat(ExampleBackendService.query(firstRequest()).test().values().get(0).getPokemons())
        .hasSize(10);
  }

  private PagingRequest firstRequest() {
    return new PagingRequest(0, new PagingQuery(null), PagingQueryConfig.DEFAULT_QUERY_CONFIG);
  }
}
