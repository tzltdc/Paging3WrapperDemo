package paging.wrapper.demo.ui.query;

import android.view.View;
import dagger.assisted.AssistedFactory;

@AssistedFactory
public interface QueryViewHolderFactory {

  QueryViewHolder create(View itemView);
}
