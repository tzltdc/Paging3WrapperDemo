package paging.wrapper;

import android.util.Log;
import androidx.test.espresso.IdlingResource;
import paging.wrapper.app.PokemonApplication;
import timber.log.Timber;

public class EspressoApp extends PokemonApplication {

  @Override
  public void onCreate() {
    super.onCreate();
    IdlingResource idlingResource = pagingIdlingResource();
    Log.d("IdlingResource", String.format("EspressoApp IdlingResource:%s", idlingResource));
    Timber.d("IdlingResource", String.format("EspressoApp IdlingResource:%s", idlingResource));
    //    IdlingRegistry.getInstance().register(idlingResource);
  }
}
