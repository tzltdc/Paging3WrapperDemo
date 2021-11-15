package paging.wrapper.model.data;

import androidx.annotation.Nullable;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class PagingQueryParam {

  @Nullable
  public abstract String searchKey();

  public static PagingQueryParam create(String newSearchKey) {
    return new AutoValue_PagingQueryParam(newSearchKey);
  }
}
