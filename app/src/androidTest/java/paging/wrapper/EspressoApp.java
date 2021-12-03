package paging.wrapper;

import paging.wrapper.app.PokemonApplication;
import paging.wrapper.di.thread.ThreadConfig;

public class EspressoApp extends PokemonApplication {

  @Override
  public void onCreate() {
    super.onCreate();
  }

  @Override
  protected ThreadConfig threadConfig() {
    return ThreadConfig.create(true);
  }
}
