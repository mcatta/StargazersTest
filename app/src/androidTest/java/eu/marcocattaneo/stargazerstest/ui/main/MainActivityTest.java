package eu.marcocattaneo.stargazerstest.ui.main;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import eu.marcocattaneo.stargazerstest.R;
import eu.marcocattaneo.stargazerstest.business.helpers.GithubProfileHelper;
import eu.marcocattaneo.stargazerstest.ui.dialog.ChangeGithubProfileDialogFragment;
import eu.marcocattaneo.stargazerstest.ui.general.BaseDialogFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        GithubProfileHelper.init(mActivityRule.getActivity());
    }

    @Test
    public void testOpeningDialogOnClick() {

        onView(withId(R.id.changeRepo)).perform(click());
        Fragment dialog = mActivityRule.getActivity().getSupportFragmentManager().findFragmentByTag(ChangeGithubProfileDialogFragment.TAG);

        assertTrue(dialog instanceof ChangeGithubProfileDialogFragment);
        assertTrue(((BaseDialogFragment) dialog).getShowsDialog());

        ((BaseDialogFragment) dialog).dismiss();
    }

    @Test
    public void testSubmitButtonNoValue() {

        onView(withId(R.id.changeRepo)).perform(click());
        Fragment dialog = mActivityRule.getActivity().getSupportFragmentManager().findFragmentByTag(ChangeGithubProfileDialogFragment.TAG);

        assertTrue(dialog instanceof ChangeGithubProfileDialogFragment);
        assertTrue(((BaseDialogFragment) dialog).getShowsDialog());

        onView(withId(R.id.input_user)).perform(clearText());
        onView(withId(R.id.input_repo)).perform(clearText());
        onView(withId(android.R.id.button1)).check(matches(not(isEnabled())));
    }

    @Test
    public void testSubmitButtonWithValue() {

        onView(withId(R.id.changeRepo)).perform(click());
        Fragment dialog = mActivityRule.getActivity().getSupportFragmentManager().findFragmentByTag(ChangeGithubProfileDialogFragment.TAG);

        assertTrue(dialog instanceof ChangeGithubProfileDialogFragment);
        assertTrue(((BaseDialogFragment) dialog).getShowsDialog());

        onView(withId(R.id.input_user)).perform(typeText("test"));
        onView(withId(R.id.input_repo)).perform(typeText("test"));
        onView(withId(android.R.id.button1)).check(matches(isEnabled()));
    }

}