package io.husayn.paging_library_sample.data;

import com.google.common.truth.Truth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class RemoteDataServerTest {

  @Test
  public void get() {
    Truth.assertThat(RemoteDataServer.all()).hasSize(151);
  }
}
