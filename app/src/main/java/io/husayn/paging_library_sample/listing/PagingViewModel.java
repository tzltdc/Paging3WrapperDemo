package io.husayn.paging_library_sample.listing;

import androidx.paging.LoadState;
import com.google.auto.value.AutoValue;
import io.husayn.paging_library_sample.data.Pokemon;
import java.util.List;

@AutoValue
public abstract class PagingViewModel {

  public abstract LoadState loadState();

  public abstract List<Pokemon> snapshot();

  public static PagingViewModel create(LoadState loadState, List<Pokemon> snapshot) {
    return new AutoValue_PagingViewModel(loadState, snapshot);
  }
}
