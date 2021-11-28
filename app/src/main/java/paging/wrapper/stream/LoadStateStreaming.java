package paging.wrapper.stream;

import androidx.annotation.VisibleForTesting;
import androidx.paging.LoadState;
import io.reactivex.Observable;
import kotlin.Unit;

public interface LoadStateStreaming {

  Observable<LoadState> footer();

  Observable<LoadState> header();

  @VisibleForTesting
  Observable<Unit> idle();
}
