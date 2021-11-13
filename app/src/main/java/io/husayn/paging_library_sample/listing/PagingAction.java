package io.husayn.paging_library_sample.listing;

import androidx.paging.LoadType;
import com.google.auto.value.AutoValue;

@AutoValue
abstract class PagingAction {

  public abstract LoadType loadType();

  public abstract PagingRequest request();

  public abstract PokemonDto response();

  public static PagingAction create(
      PokemonDto newResponse, PagingRequest newRequest, LoadType loadType) {
    return new AutoValue_PagingAction(loadType, newRequest, newResponse);
  }
}
