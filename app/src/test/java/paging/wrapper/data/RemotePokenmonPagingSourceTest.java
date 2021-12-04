package paging.wrapper.data;

import androidx.annotation.NonNull;
import androidx.paging.PagingSource.LoadParams;
import androidx.paging.PagingSource.LoadParams.Refresh;
import androidx.paging.PagingSource.LoadResult.Page;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import paging.wrapper.DaggerTestUtil;
import paging.wrapper.demo.ui.query.FilterOptionProvider;
import paging.wrapper.model.data.PagingQueryContext;
import paging.wrapper.model.data.PagingQueryParam;
import paging.wrapper.model.data.Pokemon;

public class RemotePokenmonPagingSourceTest {

  @Inject RemotePokenmonPagingSourceFactory factory;

  @Before
  public void setup() {
    DaggerTestUtil.testComponent().inject(this);
  }

  @Test
  public void whenInitialLoadRequested_shouldReceiveExpectedData() throws InterruptedException {
    factory
        .create(queryAll())
        .loadSingle(refreshLoadParam())
        .test()
        .await()
        .assertValueCount(1)
        .assertValue(refreshResult());
  }

  @NonNull
  private static Page<Integer, Pokemon> refreshResult() {
    return new Page<>(DaggerTestUtil.serverDto().list().subList(0, 10), null, 10);
  }

  private static LoadParams<Integer> refreshLoadParam() {
    return new Refresh<>(0, 2, false);
  }

  @NonNull
  private static PagingQueryContext queryAll() {
    return PagingQueryContext.create(FilterOptionProvider.ALL, PagingQueryParam.create(null));
  }
}
