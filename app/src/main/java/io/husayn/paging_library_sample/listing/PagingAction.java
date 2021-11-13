package io.husayn.paging_library_sample.listing;

import androidx.paging.LoadType;
import com.google.auto.value.AutoValue;

@AutoValue
abstract class PagingAction {

  public abstract LoadType type();

  public abstract Data data();

  public static PagingAction create(LoadType loadType, Data data) {
    return new AutoValue_PagingAction(loadType, data);
  }

  @AutoValue
  abstract static class Data {

    public abstract PagingRequest request();

    public abstract PokemonDto response();

    public static Data create(PokemonDto newResponse, PagingRequest newRequest) {
      return new AutoValue_PagingAction_Data(newRequest, newResponse);
    }
  }
}
