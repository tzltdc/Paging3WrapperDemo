package androidx.paging.wrapper;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Loading implements LoadStateBase {

  public abstract boolean endOfPaginationReached();

  public static Loading create(boolean endOfPaginationReached) {
    return new AutoValue_Loading(endOfPaginationReached);
  }
}
