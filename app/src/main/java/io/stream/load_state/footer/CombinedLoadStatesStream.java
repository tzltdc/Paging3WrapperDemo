package io.stream.load_state.footer;

import androidx.paging.CombinedLoadStates;

public interface CombinedLoadStatesStream {

  void accept(CombinedLoadStates combinedLoadStates);
}
