package io.husayn.paging_library_sample.listing;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class PageActionResult {

  public abstract PagingRequest request();

  public abstract PokemonDto response();

  public static PageActionResult create(PokemonDto response, PagingRequest request) {
    return new AutoValue_PageActionResult(request, response);
  }
}
