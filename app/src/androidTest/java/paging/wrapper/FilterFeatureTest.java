package paging.wrapper;

import static com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed;
import static com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotExist;
import static com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertTextColorIs;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import io.husayn.paging_library_sample.R;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import paging.wrapper.demo.MainActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class FilterFeatureTest {

  @Rule
  public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

  @Test
  public void case_1_byDefaultAllFilterAreCaseSensitive() {
    assertDisplayed("ALL");
    assertNotExist("all");
  }

  @Test
  public void case_2_filterWithSpaceWillNotBeFound() {
    assertNotExist("ALL ");
  }

  @Test
  public void case_3_byDefaultAllFilterOptionsAreDisplayed() {
    assertDisplayed("ALL");
    assertDisplayed("83 WITH A");
    assertDisplayed("83 WITH A");
    assertDisplayed("16 WITH B");
    assertDisplayed("12 WITH EE");
    assertDisplayed("1 IVY");
    assertDisplayed("EMPTY");
    assertDisplayed("INITIAL_LOAD_ERROR");
    assertDisplayed("LOAD_MORE_ERROR");
  }

  @Test
  public void case_4_1_colorCouldBeAsserted() {
    assertTextColorIs(R.id.tv_query, R.color.colorAccent);
  }

  @Ignore
  @Test
  public void case_4_1_selectFilterColorShouldBeHighlighted() {
    assertTextColorIs(R.id.tv_query, R.color.colorAccent);
  }
}
