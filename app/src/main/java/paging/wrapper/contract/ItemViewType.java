package paging.wrapper.contract;

import timber.log.Timber;

public class ItemViewType {

  public static final int ITEM_VIEW_TYPE_HEADER = -2;
  public static final int ITEM_VIEW_TYPE_FOOTER = -1;
  public static final int ITEM_VIEW_TYPE_BODY = 0;
  public static final int SPAN_FULL = 12;
  public static final int SPAN_HALF = 6;
  public static final int SPAN_QUARTER = 3;
  public static final int SPAN_ONE_THIRD = 4;

  public static int map(int itemViewType) {
    Timber.i("ItemViewType map:%s", itemViewType);
    switch (itemViewType) {
      case ITEM_VIEW_TYPE_HEADER:
        return SPAN_FULL;
      case ITEM_VIEW_TYPE_BODY:
        return SPAN_ONE_THIRD;
      case ITEM_VIEW_TYPE_FOOTER:
        return SPAN_HALF;
      default:
        return SPAN_QUARTER;
    }
  }
}
