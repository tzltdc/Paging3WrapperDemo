package io.husayn.paging_library_sample.listing;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class PagingAction {

  public abstract PagingRequest request();

  public abstract SearchPokemonResponse response();

  public static PagingAction create(PagingRequest newRequest, SearchPokemonResponse newResponse) {
    return new AutoValue_PagingAction(newRequest, newResponse);
  }
}
