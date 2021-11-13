package io.husayn.paging_library_sample.listing;

import androidx.paging.PagingSource;
import com.google.auto.value.AutoValue;
import io.husayn.paging_library_sample.data.Pokemon;
import kotlin.jvm.functions.Function0;

@AutoValue
public abstract class PagerContext {

  public abstract ExampleRemoteMediator exampleRemoteMediator();

  public abstract Function0<PagingSource<Integer, Pokemon>> pagingSourceFunction();

  public static PagerContext create(
      ExampleRemoteMediator exampleRemoteMediator,
      Function0<PagingSource<Integer, Pokemon>> pagingSourceFunction) {
    return new AutoValue_PagerContext(exampleRemoteMediator, pagingSourceFunction);
  }
}
