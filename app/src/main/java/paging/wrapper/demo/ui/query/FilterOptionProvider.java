package paging.wrapper.demo.ui.query;

import java.util.Arrays;
import java.util.List;
import paging.wrapper.model.data.FilterBean;
import paging.wrapper.model.data.QueryModel;

public class FilterOptionProvider {

  public static final String INITIAL_LOAD_ERROR = "initial_load_error";
  public static final String LOAD_MORE_ERROR = "load_more_error";
  public static final String ALL = "All";

  public static List<QueryModel> get() {
    return Arrays.asList(
        QueryModel.create(true, FilterBean.create(ALL, null)),
        QueryModel.create(FilterBean.create("83 with a", "a")),
        QueryModel.create(FilterBean.create("16 with b", "b")),
        QueryModel.create(FilterBean.create("12 with ee", "ee")),
        QueryModel.create(FilterBean.create("1 Ivy", "Ivy")),
        QueryModel.create(FilterBean.create("Empty", "abc")),
        QueryModel.create(FilterBean.create(INITIAL_LOAD_ERROR, null)),
        QueryModel.create(FilterBean.create(LOAD_MORE_ERROR, null)));
  }
}
