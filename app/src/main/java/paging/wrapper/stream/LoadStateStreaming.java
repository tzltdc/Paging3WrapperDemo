package paging.wrapper.stream;

import androidx.pagingx.CombinedLoadStates;
import androidx.pagingx.LoadState;
import io.reactivex.Observable;
import kotlin.Unit;

public interface LoadStateStreaming {

  Observable<LoadState> footer();

  Observable<LoadState> header();

  Observable<Unit> idle();

  Observable<CombinedLoadStates> raw();
}
