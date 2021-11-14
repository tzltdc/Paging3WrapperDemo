package io.stream.load_state.footer;

import androidx.paging.LoadState;

public interface FooterLoadStateStream {

  void accept(LoadState loadState);
}
