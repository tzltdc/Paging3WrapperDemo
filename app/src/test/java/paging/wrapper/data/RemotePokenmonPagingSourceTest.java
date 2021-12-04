package paging.wrapper.data;

import static com.google.common.truth.Truth.assertThat;

import androidx.annotation.NonNull;
import androidx.paging.PagingSource.LoadParams;
import androidx.paging.PagingSource.LoadParams.Append;
import androidx.paging.PagingSource.LoadParams.Refresh;
import androidx.paging.PagingSource.LoadResult;
import androidx.paging.PagingSource.LoadResult.Error;
import androidx.paging.PagingSource.LoadResult.Page;
import io.reactivex.observers.TestObserver;
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
  public void case_1_whenInitialLoadRequested_shouldReceiveExpectedData()
      throws InterruptedException {
    factory
        .create(queryAll())
        .loadSingle(refreshLoadParam())
        .test()
        .await()
        .assertValueCount(1)
        .assertValue(refreshResult());
  }

  @Test
  public void case_2_2_whenLoadMoreRequested_shouldReceiveExpectedData()
      throws InterruptedException {
    factory
        .create(queryAll())
        .loadSingle(appendLoadParam())
        .test()
        .await()
        .assertValueCount(1)
        .assertValue(appendResult());
  }

  @Test
  public void case_2_2_whenNoMoreData_shouldReceiveNullNextKey() throws InterruptedException {
    TestObserver<LoadResult<Integer, Pokemon>> testObserver =
        factory.create(queryAll()).loadSingle(appendLoadParamNoMore()).test().await();
    testObserver.assertValueCount(1);
    LoadResult<Integer, Pokemon> actual = testObserver.values().get(0);
    assertThat(((Page<Integer, Pokemon>) actual).getNextKey()).isNull();
  }

  @Test
  public void case_3_whenLoadMoreRequestedErrorTrigger_shouldReceiveError()
      throws InterruptedException {
    TestObserver<LoadResult<Integer, Pokemon>> testObserve =
        factory.create(queryInitialError()).loadSingle(refreshLoadParam()).test().await();
    testObserve.assertValueCount(1);
    assertThat(testObserve.values().get(0)).isInstanceOf(Error.class);
    Error<Integer, Pokemon> asError = (Error<Integer, Pokemon>) testObserve.values().get(0);
    assertThat(asError.getThrowable().getMessage()).isEqualTo(error());
  }

  @NonNull
  private static String error() {
    return "[remote]:dataSource simulated initial error";
  }

  @NonNull
  private static Page<Integer, Pokemon> appendResult() {
    return new Page<>(DaggerTestUtil.serverDto().list().subList(10, 20), null, 20);
  }

  @NonNull
  private static Page<Integer, Pokemon> refreshResult() {
    return new Page<>(DaggerTestUtil.serverDto().list().subList(0, 10), null, 10);
  }

  private static LoadParams<Integer> refreshLoadParam() {
    return new Refresh<>(0, 2, true);
  }

  private static LoadParams<Integer> appendLoadParam() {
    return new Append<>(10, 2, true);
  }

  private static LoadParams<Integer> appendLoadParamNoMore() {
    return new Append<>(150, 2, true);
  }

  @NonNull
  private static PagingQueryContext queryAll() {
    return PagingQueryContext.create(FilterOptionProvider.ALL, PagingQueryParam.create(null));
  }

  @NonNull
  private static PagingQueryContext queryInitialError() {
    return PagingQueryContext.create(
        FilterOptionProvider.INITIAL_LOAD_ERROR, PagingQueryParam.create(null));
  }
}
