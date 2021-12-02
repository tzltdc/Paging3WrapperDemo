package androidx.paging.wrapper;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class NotLoading implements LoadStateBase {

  public static final NotLoading COMPLETE = NotLoading.create(true);
  public static final NotLoading INCOMPLETE = NotLoading.create(false);

  public abstract boolean endOfPaginationReached();

  public static NotLoading create(boolean endOfPaginationReached) {
    return new AutoValue_NotLoading(endOfPaginationReached);
  }
}
