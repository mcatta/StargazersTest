package eu.marcocattaneo.stargazerstest.ui.main;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import eu.marcocattaneo.stargazerstest.business.helpers.GithubProfileHelper;
import eu.marcocattaneo.stargazerstest.ui.dialog.ChangeGithubProfileDialogFragment;
import eu.marcocattaneo.stargazerstest.ui.general.BaseDialogFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class MainActivityFirstSessionTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void onCreate() throws Exception {
        GithubProfileHelper.getInstance().reset();
    }

    @Test
    public void onResume() throws Exception {

        // Check if dialog to add user and repo is open
        Fragment dialog = mActivityRule.getActivity().getSupportFragmentManager().findFragmentByTag(ChangeGithubProfileDialogFragment.TAG);

        assertTrue(dialog instanceof ChangeGithubProfileDialogFragment);
        assertTrue(((BaseDialogFragment) dialog).getShowsDialog());

        onView(withId(android.R.id.button1)).check(matches(isEnabled()));
    }

}