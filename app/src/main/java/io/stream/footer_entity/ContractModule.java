package io.stream.footer_entity;

import dagger.Binds;
import dagger.Module;
import io.husayn.paging_library_sample.listing.PagingAdapterContract;
import io.paging.footer.ClickActionContract;
import io.paging.footer.FooterEntityContract;

@Module
public abstract class ContractModule {

  @Binds
  public abstract FooterEntityContract footerEntityContract(PagingAdapterContract impl);

  @Binds
  public abstract ClickActionContract clickActionContract(PagingAdapterContract impl);
}
