package io.husayn.paging_library_sample.listing;

class PagingAction {

  public final PagingRequest request;
  public final SearchPokemonResponse response;

  public PagingAction(PagingRequest request, SearchPokemonResponse response) {
    this.request = request;
    this.response = response;
  }
}
