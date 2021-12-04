package paging.wrapper.data;

import androidx.annotation.Nullable;
import com.google.auto.value.AutoValue;
import paging.wrapper.model.data.PokemonDto;

@AutoValue
abstract class RemotePagingResponse {

  public abstract PokemonDto response();

  @Nullable
  public abstract Integer nextTargetCount();

  public static RemotePagingResponse create(
      PokemonDto response, @Nullable Integer nextTargetCount) {
    return new AutoValue_RemotePagingResponse(response, nextTargetCount);
  }
}
