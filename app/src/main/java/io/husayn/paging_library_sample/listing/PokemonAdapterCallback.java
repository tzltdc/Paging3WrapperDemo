package io.husayn.paging_library_sample.listing;

public interface PokemonAdapterCallback {

  void notifyItemInserted(int dataItemCount);

  void notifyItemChanged(int index);

  void notifyItemRemoved(int dataItemCount);

  int dataItemCount();
}
