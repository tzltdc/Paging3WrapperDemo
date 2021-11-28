package paging.wrapper.model.data;

public class QueryModel {

  private boolean select;
  private final FilterBean filterBean;

  public QueryModel(boolean select, FilterBean filterBean) {
    this.select = select;
    this.filterBean = filterBean;
  }

  public boolean selected() {
    return select;
  }

  public FilterBean filterBean() {
    return filterBean;
  }

  public static QueryModel create(boolean selected, FilterBean filterBean) {
    return new QueryModel(selected, filterBean);
  }

  public static QueryModel create(FilterBean filterBean) {
    return create(false, filterBean);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    QueryModel that = (QueryModel) o;

    if (select != that.select) {
      return false;
    }
    return filterBean.equals(that.filterBean);
  }

  @Override
  public int hashCode() {
    int result = (select ? 1 : 0);
    result = 31 * result + filterBean.hashCode();
    return result;
  }
}
