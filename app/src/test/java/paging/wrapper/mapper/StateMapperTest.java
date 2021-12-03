package paging.wrapper.mapper;

import static com.google.common.truth.Truth.assertThat;

import androidx.pagingx.LoadState;
import androidx.pagingx.LoadState.NotLoading;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import paging.wrapper.model.ui.HeaderEntity;
import paging.wrapper.model.ui.HeaderEntity.Empty;
import paging.wrapper.model.ui.PagingViewModel;

public class StateMapperTest {

  private StateMapper stateMapper;

  @Before
  public void setup() {
    stateMapper = new StateMapper();
  }

  @Test
  public void defaultLoadingUIConfig() {

    assertThat(
            stateMapper.footerEntity(PagingViewModel.create(notLoading(), Collections.emptyList())))
        .isEqualTo(null);
  }

  private LoadState notLoading() {
    return LoadState.ofNotLoading(NotLoading.create(false));
  }

  @Test
  public void headerEntity() {
    assertThat(
            stateMapper.headerEntity(PagingViewModel.create(notLoading(), Collections.emptyList())))
        .isEqualTo(HeaderEntity.ofEmpty(Empty.create("No data at all")));
  }

  @Test
  public void footerEntity() {
    assertThat(
            stateMapper.footerEntity(PagingViewModel.create(notLoading(), Collections.emptyList())))
        .isEqualTo(null);
  }
}
