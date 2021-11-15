package paging.wrapper.di;

import dagger.Binds;
import dagger.Module;
import paging.wrapper.contract.ClickActionContract;
import paging.wrapper.contract.FooterEntityContract;
import paging.wrapper.contract.PagingAdapterContract;

@Module
public abstract class ContractModule {

  @Binds
  public abstract FooterEntityContract footerEntityContract(PagingAdapterContract impl);

  @Binds
  public abstract ClickActionContract clickActionContract(PagingAdapterContract impl);
}
