package paging.wrapper;

import paging.wrapper.app.PokemonApplication;

public class EspressoApp extends PokemonApplication {

  @Override
  public void onCreate() {
    super.onCreate();
    //    IdlingRegistry.getInstance().register(pagingIdlingResource());
  }
}
