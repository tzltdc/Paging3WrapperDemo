package androidx.paging.wrapper;

import androidx.annotation.Nullable;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class CombinedLoadStates {

  public abstract LoadState refresh();

  public abstract LoadState prepend();

  public abstract LoadState append();

  public abstract LoadStates source();

  @Nullable
  public abstract LoadStates mediator();

  public static Builder builder() {
    return new AutoValue_CombinedLoadStates.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder refresh(LoadState refresh);

    public abstract Builder prepend(LoadState prepend);

    public abstract Builder append(LoadState append);

    public abstract Builder source(LoadStates source);

    public abstract Builder mediator(@Nullable LoadStates mediator);

    public abstract CombinedLoadStates build();
  }
}
