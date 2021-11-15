package io.view.header;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class HeaderErrorAction {

  public abstract String text();

  public abstract HeaderErrorCallback callback();

  public static HeaderErrorAction create(String text, HeaderErrorCallback callback) {
    return new AutoValue_HeaderErrorAction(text, callback);
  }
}
