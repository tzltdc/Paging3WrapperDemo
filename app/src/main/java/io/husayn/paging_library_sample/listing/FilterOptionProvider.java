package io.husayn.paging_library_sample.listing;

import java.util.Arrays;
import java.util.List;

public class FilterOptionProvider {

  public static final String LOAD_MORE_ERROR = "load_more_error";
  public static final String INITIAL_LOAD_ERROR = "initial_load_error";

  public static List<FilterBean> get() {
    return Arrays.asList(
        FilterBean.create("All", null),
        FilterBean.create("83 with a", "a"),
        FilterBean.create("16 with b", "b"),
        FilterBean.create("12 with ee", "ee"),
        FilterBean.create("1 Ivy", "Ivy"),
        FilterBean.create("Empty", "abc"),
        FilterBean.create("Initial load error", INITIAL_LOAD_ERROR),
        FilterBean.create("Load more error", LOAD_MORE_ERROR));
  }
}
