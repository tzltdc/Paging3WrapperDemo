package paging.wrapper;

import io.github.android.tang.tony.test.util.GsonTestUtil;
import paging.wrapper.db.ServerDto;

public class DaggerTestUtil {

  public static TestComponent testComponent() {
    return DaggerTestComponent.factory().create(serverDto());
  }

  public static ServerDto serverDto() {
    return GsonTestUtil.from("dto.json", ServerDto.class);
  }
}
