package paging.wrapper.db;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;
import com.ryanharter.auto.value.gson.GenerateTypeAdapter;
import java.util.List;
import paging.wrapper.model.data.Pokemon;

@GenerateTypeAdapter
@AutoValue
public abstract class ServerDto {

  @SerializedName("list")
  public abstract List<Pokemon> list();

  public static ServerDto create(List<Pokemon> list) {
    return new AutoValue_ServerDto(list);
  }
}
