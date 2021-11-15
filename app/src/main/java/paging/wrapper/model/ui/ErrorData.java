package paging.wrapper.model.ui;

import androidx.annotation.Nullable;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ErrorData {

  public abstract String message();

  @Nullable
  public abstract String buttonText();

  public static ErrorData create(String message, String buttonText) {
    return new AutoValue_ErrorData(message, buttonText);
  }
}
