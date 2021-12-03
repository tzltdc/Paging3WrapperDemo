package androidx.paging.wrapper;

import androidx.paging.wrapper.LoadState.Status;
import com.google.auto.value.AutoOneOf;
import com.google.auto.value.AutoValue;
import com.ryanharter.auto.value.gson.GenerateTypeAdapter;

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
@AutoOneOf(Status.class)
@GenerateTypeAdapter
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

  public abstract Status status();

  public static LoadState ofNotLoading(NotLoading notLoading) {
    return AutoOneOf_LoadState.notLoading(notLoading);
  }

  public static LoadState ofLoading(Loading loading) {
    return AutoOneOf_LoadState.loading(loading);
  }

  public static LoadState ofError(LoadError loadError) {
    return AutoOneOf_LoadState.error(loadError);
  }

  public enum Status {
    NOT_LOADING,
    LOADING,
    ERROR
  }

  @GenerateTypeAdapter
  @AutoValue
  public abstract static class NotLoading {

    public static final NotLoading COMPLETE = NotLoading.create(true);
    public static final NotLoading INCOMPLETE = NotLoading.create(false);

    public abstract boolean endOfPaginationReached();

    public static NotLoading create(boolean endOfPaginationReached) {
      return new AutoValue_LoadState_NotLoading(endOfPaginationReached);
    }
  }

  @GenerateTypeAdapter
  @AutoValue
  public abstract static class LoadError {

    public abstract String error();

    public abstract boolean endOfPaginationReached();

    public static LoadError create(String error, boolean endOfPaginationReached) {
      return new AutoValue_LoadState_LoadError(error, endOfPaginationReached);
    }
  }

  @GenerateTypeAdapter
  @AutoValue
  public abstract static class Loading {

    public abstract boolean endOfPaginationReached();

    public static Loading create(boolean endOfPaginationReached) {
      return new AutoValue_LoadState_Loading(endOfPaginationReached);
    }
  }
}
