package paging.wrapper.model.data;

import androidx.annotation.Nullable;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class FilterBean {

  public abstract String description();

  @Nullable
  public abstract String value();

  public static FilterBean create(String description, String value) {
    return new AutoValue_FilterBean(description, value);
  }

  public static FilterBean create(String description) {
    return new AutoValue_FilterBean(description, description);
  }
}
