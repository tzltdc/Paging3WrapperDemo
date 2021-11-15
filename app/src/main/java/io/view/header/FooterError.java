package io.view.header;

import androidx.annotation.Nullable;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class FooterError {

  public abstract String message();

  @Nullable
  public abstract FooterErrorAction action();

  public static FooterError create(String message, FooterErrorAction action) {
    return new AutoValue_FooterError(message, action);
  }
}
