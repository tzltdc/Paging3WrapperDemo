package paging.wrapper;

import static com.google.common.truth.Truth.assertThat;

import androidx.annotation.Nullable;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import paging.wrapper.data.PokemonBackendService;
import paging.wrapper.model.data.PagingQueryParam;
import paging.wrapper.model.data.PagingRemoteRequestConfig;
import paging.wrapper.model.data.PagingRequest;

public class PokemonBackendServiceTest {

  @Inject PokemonBackendService pokemonBackendService;

  @Before
  public void setup() {

    DaggerTestUtil.testComponent().inject(this);
  }

  @Test
  public void initial_queryWithoutFilter_shouldReturn10() {
    assertThat(pokemonBackendService.query(firstRequest(null)).test().values().get(0).list())
        .hasSize(151);
  }

  @Test
  public void initial_queryWithFilter_shouldReturn10() {
    assertThat(pokemonBackendService.query(firstRequest("a")).test().values().get(0).list())
        .hasSize(83);
  }

  @Test
  public void initial_queryWithDoubleFilter_shouldReturn10() {
    assertThat(pokemonBackendService.query(firstRequest("aa")).test().values().get(0).list())
        .hasSize(0);
  }

  @Test
  public void initial_queryWithAb_shouldReturn10() {
    assertThat(pokemonBackendService.query(firstRequest("ab")).test().values().get(0).list())
        .hasSize(6);
  }

  @Test
  public void initial_queryWithAb_shouldReturn99() {
    assertThat(pokemonBackendService.query(firstRequest("e")).test().values().get(0).list())
        .hasSize(81);
  }

  private static PagingRequest firstRequest(@Nullable String searchKey) {
    return PagingRequest.create(
        0, PagingQueryParam.create(searchKey), PagingRemoteRequestConfig.MAX_CONFIG);
  }
}
