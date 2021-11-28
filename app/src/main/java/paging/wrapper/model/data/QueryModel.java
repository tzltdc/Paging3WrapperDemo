package paging.wrapper.model.data;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class QueryModel {

  public abstract boolean selected();

  public abstract FilterBean filterBean();

  public static QueryModel create(boolean selected, FilterBean filterBean) {
    return new AutoValue_QueryModel(selected, filterBean);
  }

  public static QueryModel create(FilterBean filterBean) {
    return create(false, filterBean);
  }
}
