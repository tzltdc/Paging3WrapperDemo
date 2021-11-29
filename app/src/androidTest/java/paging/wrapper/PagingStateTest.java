package paging.wrapper;

import static com.adevinta.android.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition;
import static com.adevinta.android.barista.assertion.BaristaListAssertions.assertListItemCount;
import static com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed;
import static com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotExist;
import static com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn;
import static com.adevinta.android.barista.interaction.BaristaListInteractions.clickListItemChild;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import com.adevinta.android.barista.rule.flaky.AllowFlaky;
import com.adevinta.android.barista.rule.flaky.FlakyTestRule;
import io.husayn.paging_library_sample.R;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import paging.wrapper.demo.MainActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PagingStateTest {

  private final FlakyTestRule flakyRule = new FlakyTestRule();

  @Rule
  public ActivityScenarioRule<MainActivity> activityRule =
      new ActivityScenarioRule<>(MainActivity.class);

  @Rule public RuleChain chain = RuleChain.outerRule(flakyRule).around(activityRule);

  @Test
  public void case_0_byDefaultItIsIdlingState() {
    assertDisplayed("Idle");
  }

  @AllowFlaky(attempts = 30)
  @Test
  public void case_1_whenNoMatchedQuery_shouldShowEmptyView() {
    clickListItemChild(R.id.rv_query, 5, R.id.tv_query);

    activityRule.getScenario().onActivity(this::register);

    assertDisplayed("No data at all");
    QueryStateAsserter.assertQueryTextColor(5, R.color.colorAccent);
  }

  private void register(MainActivity activity) {
    IdlingRegistry.getInstance().register(activity.pagingIdlingResource());
  }

  @AllowFlaky(attempts = 30)
  @Test
  public void case_2_whenOnlyOneResultReturns_shouldNotShowFooterView() {
    clickListItemChild(R.id.rv_query, 4, R.id.tv_query);

    activityRule.getScenario().onActivity(this::register);

    assertDisplayed("[1]:#002");

    assertNotExist("No more data");
  }

  @AllowFlaky(attempts = 30)
  @Test
  public void case_3_1_when12ResultsReturns_shouldLoadAllDataAndFooter() {
    clickOn("12 WITH EE");

    activityRule.getScenario().onActivity(this::register);

    assertDisplayed("[1]:#012");
    assertDisplayed("[9]:#106");

    assertDisplayedAtPosition(R.id.rv_pokemons, 11, R.id.tv_pokemon_id, "[12]:#133");
    assertDisplayedAtPosition(R.id.rv_pokemons, 12, R.id.tv_footer_no_more_hint, "No more data");

    assertListItemCount(R.id.rv_pokemons, 13);
  }

  @AllowFlaky(attempts = 30)
  @Test
  public void case_3_2_when16ResultsReturns_shouldLoadAllDataAndFooter() {
    clickOn("16 WITH B");

    activityRule.getScenario().onActivity(this::register);

    assertDisplayed("[1]:#001");
    assertDisplayed("[9]:#071");

    assertDisplayedAtPosition(R.id.rv_pokemons, 15, R.id.tv_pokemon_id, "[16]:#141");
    assertDisplayedAtPosition(R.id.rv_pokemons, 16, R.id.tv_footer_no_more_hint, "No more data");

    assertListItemCount(R.id.rv_pokemons, 17);
  }

  @AllowFlaky(attempts = 30)
  @Test
  public void case_4_when83ResultsReturns_shouldLoadPartialDataAndFooter() {
    clickOn("83 WITH A");

    activityRule.getScenario().onActivity(this::register);

    assertDisplayed("[1]:#001");
    assertDisplayed("[9]:#010");
    assertDisplayedAtPosition(R.id.rv_pokemons, 8, R.id.tv_pokemon_id, "[9]:#010");

    assertDisplayed("Loading more");
  }
}
