package io.stream.paging;

import io.husayn.paging_library_sample.data.Pokemon;
import java.util.List;

public interface PagingDataListSnapshotProvider {

  List<Pokemon> snapshot();
}
