package paging.wrapper.test;

import kotlin.Unit;

public interface AppIdleStateStream {

  void accept(Unit unit);

  void clear();
}
