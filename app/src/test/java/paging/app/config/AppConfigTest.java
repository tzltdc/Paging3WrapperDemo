package paging.app.config;

import static org.junit.Assert.*;

import com.google.common.truth.Truth;
import org.junit.Test;
import paging.wrapper.app.config.AppConfig;

public class AppConfigTest {

  @Test
  public void initializeDatabase() {
    Truth.assertThat(AppConfig.DEFAULT_CONFIG.initializeDatabase()).isFalse();
  }
}
