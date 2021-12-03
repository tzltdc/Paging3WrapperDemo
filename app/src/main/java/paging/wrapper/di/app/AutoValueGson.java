package paging.wrapper.di.app;

import com.google.gson.Gson;

public class AutoValueGson {

  private Gson gson;

  public AutoValueGson(Gson gson) {
    this.gson = gson;
  }

  public Gson get() {
    return gson;
  }
}
