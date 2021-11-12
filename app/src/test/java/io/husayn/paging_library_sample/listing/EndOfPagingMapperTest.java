package io.husayn.paging_library_sample.listing;

import static com.google.common.truth.Truth.assertThat;

import io.husayn.paging_library_sample.data.Pokemon;
import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class EndOfPagingMapperTest {

  @Test
  public void whenEmpty_shouldReturnTrue() {
    assertThat(EndOfPagingMapper.endOfPaging(PagingAction.create(request(10), emptyResponse())))
        .isTrue();
  }

  @Test
  public void whenResponseLessThanRequestedShouldReturnTrue() {
    assertThat(EndOfPagingMapper.endOfPaging(PagingAction.create(request(2), single()))).isTrue();
  }

  @Test
  public void whenResponseEqualWithRequestedShouldReturnFalse() {
    assertThat(EndOfPagingMapper.endOfPaging(PagingAction.create(request(1), single()))).isFalse();
  }

  private SearchPokemonResponse emptyResponse() {
    return SearchPokemonResponse.create(Collections.emptyList());
  }

  private SearchPokemonResponse single() {
    return SearchPokemonResponse.create(Collections.singletonList(new Pokemon(0, "test")));
  }

  private PagingRequest request(int countPerPage) {
    return PagingRequest.create(
        0, PagingQuery.create(null), PagingQueryConfig.create(countPerPage));
  }
}