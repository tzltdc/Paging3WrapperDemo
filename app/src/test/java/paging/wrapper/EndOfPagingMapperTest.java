package paging.wrapper;

import static com.google.common.truth.Truth.assertThat;

import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import paging.wrapper.data.EndOfPagingMapper;
import paging.wrapper.model.data.PageActionResult;
import paging.wrapper.model.data.PagingQueryParam;
import paging.wrapper.model.data.PagingRemoteRequestConfig;
import paging.wrapper.model.data.PagingRequest;
import paging.wrapper.model.data.Pokemon;
import paging.wrapper.model.data.PokemonDto;

@RunWith(RobolectricTestRunner.class)
public class EndOfPagingMapperTest {

  @Test
  public void whenEmpty_shouldReturnTrue() {
    assertThat(EndOfPagingMapper.endOfPaging(PageActionResult.create(emptyResponse(), request(10))))
        .isTrue();
  }

  @Test
  public void whenResponseLessThanRequestedShouldReturnTrue() {
    assertThat(EndOfPagingMapper.endOfPaging((PageActionResult.create(single(), request(2)))))
        .isTrue();
  }

  @Test
  public void whenResponseEqualWithRequestedShouldReturnFalse() {
    assertThat(EndOfPagingMapper.endOfPaging((PageActionResult.create(single(), request(1)))))
        .isFalse();
  }

  private PokemonDto emptyResponse() {
    return PokemonDto.create(Collections.emptyList());
  }

  private PokemonDto single() {
    return PokemonDto.create(Collections.singletonList(new Pokemon(0, "test")));
  }

  private PagingRequest request(int countPerPage) {
    return PagingRequest.create(
        0, PagingQueryParam.create(null), PagingRemoteRequestConfig.create(countPerPage));
  }
}
