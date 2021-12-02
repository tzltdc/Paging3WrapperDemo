package androidx.paging.wrapper;

import com.google.auto.value.AutoOneOf;

/**
 * LoadState of a PagedList load - associated with a LoadType.
 *
 * <p>LoadState of any LoadType may be observed for UI purposes by registering a listener via
 * androidx.paging.PagingDataAdapter.addLoadStateListener or
 * androidx.paging.AsyncPagingDataDiffer.addLoadStateListener
 *
 * <p>Params: endOfPaginationReached - false if there is more data to load in the LoadType this
 * LoadState is associated with, true otherwise. This parameter informs Pager if it should continue
 * to make requests for additional data in this direction or if it should halt as the end of the
 * dataset has been reached. Note: The LoadTypeLoadType.REFRESH always has
 * LoadState.endOfPaginationReached set to false.
 */
@AutoOneOf(LoadStatus.class)
public abstract class LoadState {

  public static final LoadState INCOMPLETE = LoadState.ofNotLoading(NotLoading.INCOMPLETE);

  /**
   * Indicates the PagingData is not currently loading, and no error currently observed. Params:
   * endOfPaginationReached - false if there is more data to load in the LoadType this LoadState is
   * associated with, true otherwise. This parameter informs Pager if it should continue to make
   * requests for additional data in this direction or if it should halt as the end of the dataset
   * has been reached.
   */
  public abstract NotLoading notLoading();

  /** Loading is in progress. */
  public abstract Loading loading();

  /**
   * Loading hit an error. Params: error - Throwable that caused the load operation to generate this
   * error state. See Also: androidx.paging.PagedList.retry
   */
  public abstract LoadError error();

  public abstract LoadStatus status();

  public static LoadState ofNotLoading(NotLoading notLoading) {
    return AutoOneOf_LoadState.notLoading(notLoading);
  }

  public static LoadState ofLoading(Loading loading) {
    return AutoOneOf_LoadState.loading(loading);
  }

  public static LoadState ofError(LoadError loadError) {
    return AutoOneOf_LoadState.error(loadError);
  }
}
