package paging.wrapper.model.data;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class PageActionResult {

  public abstract PagingRequest request();

  public abstract PokemonDto response();

  public static PageActionResult create(PokemonDto response, PagingRequest request) {
    return new AutoValue_PageActionResult(request, response);
  }
}
