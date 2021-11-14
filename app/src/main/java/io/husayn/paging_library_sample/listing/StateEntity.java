package io.husayn.paging_library_sample.listing;

import com.google.auto.value.AutoOneOf;
import io.view.header.FooterEntity;
import io.view.header.HeaderEntity;

@AutoOneOf(StateEntity.State.class)
public abstract class StateEntity {

  public abstract State state();

  public abstract HeaderEntity header();

  public abstract void body();

  public abstract FooterEntity footer();

  public static StateEntity ofHeader(HeaderEntity headerEntity) {
    return AutoOneOf_StateEntity.header(headerEntity);
  }

  public static StateEntity ofFooter(FooterEntity footerEntity) {
    return AutoOneOf_StateEntity.footer(footerEntity);
  }

  public static StateEntity ofBody() {
    return AutoOneOf_StateEntity.body();
  }

  public enum State {
    HEADER,
    BODY,
    FOOTER
  }
}
