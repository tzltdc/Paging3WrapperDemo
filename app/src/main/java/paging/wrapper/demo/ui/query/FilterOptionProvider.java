package paging.wrapper.demo.ui.query;

import java.util.Arrays;
import java.util.List;
import paging.wrapper.model.data.FilterBean;

public class FilterOptionProvider {

  public static final String INITIAL_LOAD_ERROR = "initial_load_error";
  public static final String LOAD_MORE_ERROR = "load_more_error";
  public static final String ALL = "All";

  public static List<FilterBean> get() {
    return Arrays.asList(
        FilterBean.create(ALL, null),
        FilterBean.create("83 with a", "a"),
        FilterBean.create("16 with b", "b"),
        FilterBean.create("12 with ee", "ee"),
        FilterBean.create("1 Ivy", "Ivy"),
        FilterBean.create("Empty", "abc"),
        FilterBean.create(INITIAL_LOAD_ERROR, null),
        FilterBean.create(LOAD_MORE_ERROR, null));
  }
}
