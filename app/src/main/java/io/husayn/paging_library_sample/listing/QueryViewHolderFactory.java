package io.husayn.paging_library_sample.listing;

import android.view.View;
import dagger.assisted.AssistedFactory;

@AssistedFactory
interface QueryViewHolderFactory {

  QueryViewHolder create(View itemView);
}
