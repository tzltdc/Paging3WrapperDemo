package paging.demo.ui.query;

import static org.junit.Assert.*;

import com.google.common.truth.Truth;
import org.junit.Test;
import paging.wrapper.demo.ui.query.QueryStreamImpl;
import paging.wrapper.model.data.FilterBean;

public class QueryStreamImplTest {

  @Test
  public void peek() {

    QueryStreamImpl impl = new QueryStreamImpl();
    impl.accept(FilterBean.create("1"));
    Truth.assertThat(impl.peek().description()).isEqualTo("1");
  }
}
