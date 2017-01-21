package eu.marcocattaneo.stargazerstest.ui.main;

import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;

import org.junit.Rule;
import org.junit.Test;

import eu.marcocattaneo.stargazerstest.R;
import eu.marcocattaneo.stargazerstest.business.helpers.GithubProfileHelper;
import eu.marcocattaneo.stargazerstest.ui.dialog.ChangeGithubProfileDialogFragment;
import eu.marcocattaneo.stargazerstest.ui.general.BaseDialogFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void onCreate() throws Exception {
        GithubProfileHelper.geInstance().reset();
    }

    @Test
    public void onResume() throws Exception {

        Fragment dialog = mActivityRule.getActivity().getSupportFragmentManager().findFragmentByTag(ChangeGithubProfileDialogFragment.TAG);

        assertTrue(dialog instanceof ChangeGithubProfileDialogFragment);
        assertTrue(((BaseDialogFragment) dialog).getShowsDialog());

        ((BaseDialogFragment) dialog).dismiss();
    }

    @Test
    public void testOpeningDialogOnClick() {
        GithubProfileHelper.init(mActivityRule.getActivity());
        GithubProfileHelper.geInstance().set("user", "pass");

        onView(withId(R.id.changeRepo)).perform(click());
        Fragment dialog = mActivityRule.getActivity().getSupportFragmentManager().findFragmentByTag(ChangeGithubProfileDialogFragment.TAG);

        assertTrue(dialog instanceof ChangeGithubProfileDialogFragment);
        assertTrue(((BaseDialogFragment) dialog).getShowsDialog());

        GithubProfileHelper.geInstance().reset();
    }

}