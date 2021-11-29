package paging.wrapper;

import static com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed;
import static com.adevinta.android.barista.interaction.BaristaListInteractions.clickListItemChild;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import io.husayn.paging_library_sample.R;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import paging.wrapper.demo.MainActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EmptyVIewStateTest {

  @Rule
  public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

  @Test
  public void case_0_selectFilterColorShouldBeHighlighted() {
    clickListItemChild(R.id.rv_query, 5, R.id.tv_query);

    EspressoApp espressoApp = (EspressoApp) ApplicationProvider.getApplicationContext();
    IdlingRegistry.getInstance().register(espressoApp.pagingIdlingResource());

    assertDisplayed("No data at all");
    QueryStateAsserter.assertQueryTextColor(5, R.color.colorAccent);
  }
}
