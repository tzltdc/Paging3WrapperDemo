package androidx.paging.wrapper;

/**
 * Type of load a [PagingData] can trigger a [PagingSource] to perform.
 *
 * <p>[LoadState] of any [LoadType] may be observed for UI purposes by registering a listener via
 * [androidx.paging.PagingDataAdapter.addLoadStateListener] or
 * [androidx.paging.AsyncPagingDataDiffer.addLoadStateListener].
 *
 * @see LoadState
 */
public enum LoadType {
  /**
   * [PagingData] content being refreshed, which can be a result of [PagingSource] invalidation,
   * refresh that may contain content updates, or the initial load.
   */
  REFRESH,

  /** Load at the start of a [PagingData]. */
  PREPEND,

  /** Load at the end of a [PagingData]. */
  APPEND
}
