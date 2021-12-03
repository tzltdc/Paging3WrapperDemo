package paging.wrapper.model.ui;

import androidx.pagingx.LoadState;
import com.google.auto.value.AutoValue;
import java.util.List;
import paging.wrapper.model.data.Pokemon;

@AutoValue
public abstract class PagingViewModel {

  public abstract LoadState loadState();

  public abstract List<Pokemon> snapshot();

  public static PagingViewModel create(LoadState loadState, List<Pokemon> snapshot) {
    return new AutoValue_PagingViewModel(loadState, snapshot);
  }
}
