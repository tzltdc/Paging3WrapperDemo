package io.husayn.paging_library_sample.listing;

import androidx.annotation.LayoutRes;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class FooterConfig {

  @LayoutRes
  public abstract int layoutId();

  public static FooterConfig create(@LayoutRes int layoutId) {
    return new AutoValue_FooterConfig(layoutId);
  }
}
