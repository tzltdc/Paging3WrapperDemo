package paging.wrapper.data;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import javax.inject.Inject;
import paging.wrapper.model.data.PagingQueryContext;
import paging.wrapper.model.data.Pokemon;

public class RemotePagerFactoryImpl implements PagerFactory {

  private final PagingConfig androidPagingConfig;
  private final RemotePokenmonPagingSourceFactory remotePokenmonPagingSourceFactory;

  @Inject
  public RemotePagerFactoryImpl(
      PagingConfig androidPagingConfig,
      RemotePokenmonPagingSourceFactory remotePokenmonPagingSourceFactory) {
    this.androidPagingConfig = androidPagingConfig;
    this.remotePokenmonPagingSourceFactory = remotePokenmonPagingSourceFactory;
  }

  @Override
  public Pager<Integer, Pokemon> pager(PagingQueryContext query) {
    return new Pager<>(
        androidPagingConfig,
        PagerFactory.INITIAL_LOAD_KEY,
        null,
        remotePokenmonPagingSourceFactory.create(query));
  }
}
