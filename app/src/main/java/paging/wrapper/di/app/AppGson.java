package paging.wrapper.di.app;

import com.google.gson.Gson;

public class AppGson {

  private Gson gson;

  public AppGson(Gson gson) {
    this.gson = gson;
  }

  public Gson get() {
    return gson;
  }
}
