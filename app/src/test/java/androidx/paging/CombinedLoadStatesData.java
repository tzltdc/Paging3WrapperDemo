package androidx.paging;

import androidx.paging.LoadState.NotLoading;

public class CombinedLoadStatesData {

  public static final CombinedLoadStates RANDOM =
      new CombinedLoadStates(
          new NotLoading(true),
          new NotLoading(false),
          new NotLoading(false),
          new LoadStates(new NotLoading(false), new NotLoading(true), new NotLoading(true)),
          new LoadStates(new NotLoading(true), new NotLoading(false), new NotLoading(true)));
}
