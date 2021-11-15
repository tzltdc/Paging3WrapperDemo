package io.view.header;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class FooterErrorAction {

  public abstract String text();

  public abstract FooterErrorCallback callback();

  public static FooterErrorAction create(String text, FooterErrorCallback callback) {
    return new AutoValue_FooterErrorAction(text, callback);
  }
}
