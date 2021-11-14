package io.husayn.paging_library_sample.listing;

import static com.google.common.truth.Truth.assertThat;

import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.listing.PagingAction.Data;
import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class EndOfPagingMapperTest {

  @Test
  public void whenEmpty_shouldReturnTrue() {
    assertThat(EndOfPagingMapper.endOfPaging(Data.create(emptyResponse(), request(10)))).isTrue();
  }

  @Test
  public void whenResponseLessThanRequestedShouldReturnTrue() {
    assertThat(EndOfPagingMapper.endOfPaging((Data.create(single(), request(2))))).isTrue();
  }

  @Test
  public void whenResponseEqualWithRequestedShouldReturnFalse() {
    assertThat(EndOfPagingMapper.endOfPaging((Data.create(single(), request(1))))).isFalse();
  }

  private PokemonDto emptyResponse() {
    return PokemonDto.create(Collections.emptyList());
  }

  private PokemonDto single() {
    return PokemonDto.create(Collections.singletonList(new Pokemon(0, "test")));
  }

  private PagingRequest request(int countPerPage) {
    return PagingRequest.create(
        0, PagingQueryParam.create(null), PagingQueryConfig.create(countPerPage));
  }
}
