package androidx.pagingx;

import androidx.paging.CombinedLoadStates;
import androidx.paging.LoadState.NotLoading;
import androidx.paging.LoadStates;

public class CombinedLoadStatesData {

  public static final androidx.paging.CombinedLoadStates RANDOM =
      new CombinedLoadStates(
          new NotLoading(true),
          new NotLoading(false),
          new NotLoading(false),
          new androidx.paging.LoadStates(
              new NotLoading(false), new NotLoading(true), new NotLoading(true)),
          new LoadStates(new NotLoading(true), new NotLoading(false), new NotLoading(true)));
}
