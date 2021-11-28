package paging.wrapper.test;

import io.reactivex.Observable;
import kotlin.Unit;

public interface AppIdleStateStreaming {

  Observable<Unit> idling();

  boolean peek();
}
