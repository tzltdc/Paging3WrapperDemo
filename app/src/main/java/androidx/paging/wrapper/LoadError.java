package androidx.paging.wrapper;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class LoadError implements LoadStateBase {

  public abstract Throwable error();

  public abstract boolean endOfPaginationReached();

  public static LoadError create(Throwable error, boolean endOfPaginationReached) {
    return new AutoValue_LoadError(error, endOfPaginationReached);
  }
}
