package paging.wrapper;

import com.google.common.truth.Truth;
import io.github.android.tang.tony.test.util.GsonTestUtil;
import java.util.List;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import paging.wrapper.data.PokemonBackendService;
import paging.wrapper.db.RemoteDataServer;
import paging.wrapper.db.ServerDto;
import paging.wrapper.mapper.OffsetHelper;
import paging.wrapper.model.data.PagingQueryParam;
import paging.wrapper.model.data.PagingRemoteRequestConfig;
import paging.wrapper.model.data.PagingRequest;
import paging.wrapper.model.data.Pokemon;

public class OffsetHelperTest {

  @Inject OffsetHelper offsetHelper;
  @Inject RemoteDataServer remoteDataServer;
  @Inject PokemonBackendService pokemonBackendService;

  @Before
  public void setup() {

    DaggerTestComponent.factory().create(testData()).inject(this);
  }

  private ServerDto testData() {
    return GsonTestUtil.from("dto.json", ServerDto.class);
  }

  @Test
  public void whenQueryIsEmpty() {
    long offset =
        offsetHelper.offset(remoteDataServer.get().get(10), PagingQueryParam.create(null));
    Truth.assertThat(offset).isEqualTo(11);
  }

  @Test
  public void whenQueryIsNotEmpty() {
    long offset =
        offsetHelper.offset(remoteDataServer.get().get(1), PagingQueryParam.create("Ivy"));
    Truth.assertThat(offset).isEqualTo(1);
  }

  @Test
  public void whenQueryIsNotxxEmpty() {
    long offset =
        offsetHelper.offset(
            remoteDataServer.indexBy("31:Nidoqueen"), PagingQueryParam.create("ee"));
    Truth.assertThat(offset).isEqualTo(4);
  }

  @Test
  public void e2e() {

    long offset =
        offsetHelper.offset(
            remoteDataServer.indexBy("31:Nidoqueen"), PagingQueryParam.create("ee"));
    List<Pokemon> nextBatch =
        pokemonBackendService
            .query(
                PagingRequest.create(
                    offset, PagingQueryParam.create("ee"), PagingRemoteRequestConfig.create(4)))
            .test()
            .values()
            .get(0)
            .list();

    Truth.assertThat(nextBatch.get(0)).isEqualTo(remoteDataServer.indexBy("70:Weepinbell"));
    Truth.assertThat(nextBatch.get(1)).isEqualTo(remoteDataServer.indexBy("71:Victreebel"));
    Truth.assertThat(nextBatch.get(2)).isEqualTo(remoteDataServer.indexBy("86:Seel"));
    Truth.assertThat(nextBatch.get(3)).isEqualTo(remoteDataServer.indexBy("96:Drowzee"));
  }
}
