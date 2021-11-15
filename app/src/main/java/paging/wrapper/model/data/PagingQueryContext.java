package paging.wrapper.model.data;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class PagingQueryContext {

  public abstract String description();

  public abstract PagingQueryParam param();

  public static PagingQueryContext create(String description, PagingQueryParam param) {
    return new AutoValue_PagingQueryContext(description, param);
  }
}
