package io.husayn.paging_library_sample.listing;

import java.util.Arrays;
import java.util.List;

public class FilterOptionProvider {

  public static final String EMPTY = "Empty";

  public static List<String> get() {
    return Arrays.asList(EMPTY, "a", "b", "ee", "abc");
  }
}
