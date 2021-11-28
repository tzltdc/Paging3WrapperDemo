package paging.wrapper.demo.ui.query;

import dagger.Binds;
import dagger.Provides;
import java.util.List;
import paging.wrapper.demo.MainActivity;
import paging.wrapper.di.app.ActivityScope;
import paging.wrapper.model.data.QueryModel;

@dagger.Module
public abstract class FeatureQueryModule {

  @ActivityScope
  @Provides
  public static List<QueryModel> query() {
    return FilterOptionProvider.get();
  }

  @ActivityScope
  @Provides
  public static QueryStreamImpl queryStreamImpl() {
    return new QueryStreamImpl();
  }

  @Binds
  public abstract QueryStream bindQueryStream(QueryStreamImpl impl);

  @Binds
  public abstract QueryStreaming bindQueryStreaming(QueryStreamImpl impl);

  @Binds
  public abstract QueryViewHolder.QueryCallback queryViewHolderQueryCallback(
      MainActivity mainActivity);
}
