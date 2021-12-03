package paging.wrapper.stream;

import androidx.pagingx.CombinedLoadStates;
import androidx.pagingx.LoadState.Status;
import androidx.pagingx.LoadStates;
import javax.annotation.Nullable;

public class LoadingStateIdleMapper {

  /**
   * [CombinedLoadStates] will be called excessively from paging internal library. And its behavior
   * is quite chaotic(One could refer to the raw_log.md as references).
   *
   * <p>What is known is that
   *
   * <p>1, whenever the data loading action is concluded, there will be at least a
   * [CombinedLoadStates] that all sub state are NotLoading.
   *
   * <p>2, Such state could be duplicated.
   *
   * <p>Based on these learnings, we are to introduce this logic.
   */
  public static boolean allIdle(CombinedLoadStates combined) {
    return combinedStatusNotLoading(combined)
        && loadStatesNotLoading(combined.getSource())
        && loadStatesNotLoading(combined.getMediator());
  }

  private static boolean combinedStatusNotLoading(CombinedLoadStates combined) {
    return notLoading(LoadStates.fromCombinedLoadStates(combined));
  }

  private static boolean loadStatesNotLoading(@Nullable LoadStates loadStates) {
    return loadStates == null || notLoading(loadStates);
  }

  private static boolean notLoading(LoadStates states) {
    return states.getAppend().status().equals(Status.NOT_LOADING)
        && states.getPrepend().status().equals(Status.NOT_LOADING)
        && states.getRefresh().status().equals(Status.NOT_LOADING);
  }
}
