package io.husayn.paging_library_sample.data;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import io.husayn.paging_library_sample.listing.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

  @Rule
  public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

  @Test
  public void onAppStarts_shouldAllData() {
    ViewInteraction totalViewCount = onView(withText("total:151"));
    totalViewCount.check(matches(isDisplayed()));

    ViewInteraction absentViewCount = onView(withText("total:152"));
    absentViewCount.check(doesNotExist());
  }

  @Test
  public void onAppStarts_shouldShowAllFilters() {
    ViewInteraction validFilter = onView(withText("ABC"));
    validFilter.check(matches(isDisplayed()));

    ViewInteraction invalidFilter = onView(withText("abc"));
    invalidFilter.check(matches(isDisplayed()));
  }
}
