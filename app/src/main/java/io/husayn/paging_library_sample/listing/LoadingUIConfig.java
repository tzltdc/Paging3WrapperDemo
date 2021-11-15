package io.husayn.paging_library_sample.listing;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class LoadingUIConfig {

  public abstract String loadingHeader();

  public abstract String loadingHeaderError();

  public abstract String loadingRetry();

  public abstract String emptyData();

  public abstract String loadingMore();

  public abstract String loadingMoreError();

  public abstract String loadingMoreRetry();

  public abstract String noMoreData();

  /** @return the minimum data count to show the no more data. */
  public abstract int miniDataSize();

  public static Builder builder() {
    return new AutoValue_LoadingUIConfig.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder loadingHeader(String loadingHeader);

    public abstract Builder loadingHeaderError(String loadingHeaderError);

    public abstract Builder loadingRetry(String loadingRetry);

    public abstract Builder emptyData(String emptyData);

    public abstract Builder loadingMore(String loadingMore);

    public abstract Builder loadingMoreError(String loadingMoreError);

    public abstract Builder loadingMoreRetry(String loadingMoreRetry);

    public abstract Builder noMoreData(String noMoreData);

    public abstract Builder miniDataSize(int miniDataSize);

    public abstract LoadingUIConfig build();
  }
}
