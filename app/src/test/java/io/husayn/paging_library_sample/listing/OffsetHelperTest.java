package io.husayn.paging_library_sample.listing;

import com.google.common.truth.Truth;
import io.husayn.paging_library_sample.data.Pokemon;
import io.husayn.paging_library_sample.data.RemoteDataServer;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class OffsetHelperTest {

  @Test
  public void whenQueryIsEmpty() {
    long offset = OffsetHelper.offset(RemoteDataServer.all().get(10), PagingQuery.create(null));
    Truth.assertThat(offset).isEqualTo(11);
  }

  @Test
  public void whenQueryIsNotEmpty() {
    long offset = OffsetHelper.offset(RemoteDataServer.all().get(1), PagingQuery.create("Ivy"));
    Truth.assertThat(offset).isEqualTo(1);
  }

  @Test
  public void whenQueryIsNotxxEmpty() {
    long offset =
        OffsetHelper.offset(RemoteDataServer.indexBy("Nidoqueen"), PagingQuery.create("ee"));
    Truth.assertThat(offset).isEqualTo(4);
  }

  @Test
  public void e2e() {

    long offset =
        OffsetHelper.offset(RemoteDataServer.indexBy("Nidoqueen"), PagingQuery.create("ee"));
    List<Pokemon> nextBatch =
        PokemonBackendService.query(
                PagingRequest.create(offset, PagingQuery.create("ee"), PagingQueryConfig.create(4)))
            .test()
            .values()
            .get(0)
            .list();

    Truth.assertThat(nextBatch.get(0)).isEqualTo(RemoteDataServer.indexBy("Weepinbell"));
    Truth.assertThat(nextBatch.get(1)).isEqualTo(RemoteDataServer.indexBy("Victreebel"));
    Truth.assertThat(nextBatch.get(2)).isEqualTo(RemoteDataServer.indexBy("Seel"));
    Truth.assertThat(nextBatch.get(3)).isEqualTo(RemoteDataServer.indexBy("Drowzee"));
  }
}
