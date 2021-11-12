package io.husayn.paging_library_sample.listing;

import com.google.common.truth.Truth;
import io.husayn.paging_library_sample.data.RemoteDataServer;
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
    Truth.assertThat(offset).isEqualTo(2);
  }
}
