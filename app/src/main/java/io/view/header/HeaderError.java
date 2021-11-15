package io.view.header;

import androidx.annotation.Nullable;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class HeaderError {

  public abstract String message();

  @Nullable
  public abstract HeaderErrorAction action();

  public static HeaderError create(String message, HeaderErrorAction action) {
    return new AutoValue_HeaderError(message, action);
  }
}
