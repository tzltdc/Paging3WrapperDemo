package io.stream.load_state.footer;

import androidx.paging.LoadState;
import io.reactivex.Observable;

public interface LoadStateStreaming {

  Observable<LoadState> footer();

  Observable<LoadState> header();
}
