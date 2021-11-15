package paging.wrapper;

import com.google.common.truth.Truth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import paging.wrapper.db.RemoteDataServer;

@RunWith(RobolectricTestRunner.class)
public class RemoteDataServerTest {

  @Test
  public void get() {
    Truth.assertThat(RemoteDataServer.all()).hasSize(151);
  }
}
