package androidx.paging.wrapper;

import com.google.auto.value.AutoOneOf;

@AutoOneOf(LoadStatus.class)
public abstract class LoadState {

  public static final LoadState INCOMPLETE = LoadState.ofNotLoading(NotLoading.INCOMPLETE);

  public abstract NotLoading notLoading();

  public abstract Loading loading();

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
