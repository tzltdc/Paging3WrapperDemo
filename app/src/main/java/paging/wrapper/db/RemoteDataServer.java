package paging.wrapper.db;

import android.content.res.Resources;
import io.husayn.paging_library_sample.R;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import paging.wrapper.di.app.AppScope;
import paging.wrapper.model.data.Pokemon;

@AppScope
public class RemoteDataServer {

  private static final int MAX = 151;
  private final List<Pokemon> list;

  @Inject
  public RemoteDataServer(ServerDto dto) {
    this.list = Collections.unmodifiableList(trim(dto.list()));
  }

  public List<Pokemon> get() {
    return list;
  }

  private static List<Pokemon> trim(List<Pokemon> rawList) {
    return rawList.subList(0, Math.min(rawList.size(), MAX));
  }

  public Pokemon indexBy(String name) {
    return Observable.fromIterable(get()).filter(item -> item.name.equals(name)).blockingFirst();
  }

  public static List<Pokemon> fromResource(Resources resources) {
    List<Pokemon> list = new ArrayList<>();
    String[] name = resources.getStringArray(R.array.pokemon_names);
    int index = 0;
    for (String pokemonName : name) {
      list.add(new Pokemon(++index, pokemonName));
    }
    return list;
  }
}
