package paging.wrapper.data;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import javax.inject.Inject;
import paging.wrapper.model.data.PagingQueryContext;
import paging.wrapper.model.data.Pokemon;

public class RemotePagerFactoryImpl implements PagerFactory {

  private final PagingConfig androidPagingConfig;
  private final RemotePagingFunctionFactory remotePagingFunctionFactory;
  private final BothRemotePokenmonPagingSourceFactory bothRemotePokenmonPagingSourceFactory;

  @Inject
  public RemotePagerFactoryImpl(
      PagingConfig androidPagingConfig,
      RemotePagingFunctionFactory remotePagingFunctionFactory,
      BothRemotePokenmonPagingSourceFactory bothRemotePokenmonPagingSourceFactory) {
    this.androidPagingConfig = androidPagingConfig;
    this.remotePagingFunctionFactory = remotePagingFunctionFactory;
    this.bothRemotePokenmonPagingSourceFactory = bothRemotePokenmonPagingSourceFactory;
  }

  @Override
  public Pager<Integer, Pokemon> pager(PagingQueryContext query) {
    return new Pager<>(
        androidPagingConfig,
        PagerFactory.INITIAL_LOAD_KEY,
        null,
        bothRemotePokenmonPagingSourceFactory.create(query));
  }
}
