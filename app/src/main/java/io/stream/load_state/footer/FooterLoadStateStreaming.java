package io.stream.load_state.footer;

import androidx.paging.LoadState;
import io.reactivex.Observable;

public interface FooterLoadStateStreaming {

  Observable<LoadState> streaming();
}
