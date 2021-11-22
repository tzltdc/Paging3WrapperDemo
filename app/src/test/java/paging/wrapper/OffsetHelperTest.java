package paging.wrapper;

import com.google.common.truth.Truth;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import paging.wrapper.data.PokemonBackendService;
import paging.wrapper.db.RemoteDataServer;
import paging.wrapper.mapper.OffsetHelper;
import paging.wrapper.model.data.PagingQueryParam;
import paging.wrapper.model.data.PagingRemoteRequestConfig;
import paging.wrapper.model.data.PagingRequest;
import paging.wrapper.model.data.Pokemon;

@RunWith(RobolectricTestRunner.class)
public class OffsetHelperTest {

  @Test
  public void whenQueryIsEmpty() {
    long offset =
        OffsetHelper.offset(RemoteDataServer.all().get(10), PagingQueryParam.create(null));
    Truth.assertThat(offset).isEqualTo(11);
  }

  @Test
  public void whenQueryIsNotEmpty() {
    long offset =
        OffsetHelper.offset(RemoteDataServer.all().get(1), PagingQueryParam.create("Ivy"));
    Truth.assertThat(offset).isEqualTo(1);
  }

  @Test
  public void whenQueryIsNotxxEmpty() {
    long offset =
        OffsetHelper.offset(
            RemoteDataServer.indexBy("31:Nidoqueen"), PagingQueryParam.create("ee"));
    Truth.assertThat(offset).isEqualTo(4);
  }

  @Test
  public void e2e() {

    long offset =
        OffsetHelper.offset(
            RemoteDataServer.indexBy("31:Nidoqueen"), PagingQueryParam.create("ee"));
    List<Pokemon> nextBatch =
        PokemonBackendService.query(
                PagingRequest.create(
                    offset, PagingQueryParam.create("ee"), PagingRemoteRequestConfig.create(4)))
            .test()
            .values()
            .get(0)
            .list();

    Truth.assertThat(nextBatch.get(0)).isEqualTo(RemoteDataServer.indexBy("70:Weepinbell"));
    Truth.assertThat(nextBatch.get(1)).isEqualTo(RemoteDataServer.indexBy("71:Victreebel"));
    Truth.assertThat(nextBatch.get(2)).isEqualTo(RemoteDataServer.indexBy("86:Seel"));
    Truth.assertThat(nextBatch.get(3)).isEqualTo(RemoteDataServer.indexBy("96:Drowzee"));
  }
}