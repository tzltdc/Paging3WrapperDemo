package paging.wrapper;

import static com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed;
import static com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
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
    clickOn("EMPTY");
    assertDisplayed("Initial Loading");
    //    assertDisplayed("No data at all");
  }
}
