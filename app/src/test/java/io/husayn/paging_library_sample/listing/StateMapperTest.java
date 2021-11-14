package io.husayn.paging_library_sample.listing;

import static org.junit.Assert.*;

import com.google.common.truth.Truth;
import io.husayn.paging_library_sample.listing.StateEntity.State;
import org.junit.Ignore;
import org.junit.Test;

public class StateMapperTest {

  @Ignore("x")
  @Test
  public void stateEntity() {}

  @Test
  public void stateEntityOfBody() {

    Truth.assertThat(StateEntity.ofBody().state()).isEqualTo(State.BODY);
  }
}
