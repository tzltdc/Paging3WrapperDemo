package paging.wrapper.stream;

import androidx.paging.LoadState;
import io.reactivex.Observable;

public interface LoadStateStreaming {

  Observable<LoadState> footer();

  Observable<LoadState> header();
}
