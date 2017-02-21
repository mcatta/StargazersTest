package eu.marcocattaneo.stargazerstest.ui.main;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import eu.marcocattaneo.stargazerstest.data.GithubProfile;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterImplTest {

    @Mock
    MainPresenter mainPresenter;

    @Mock
    Context context;

    MainPresenterImpl presenter;

    String RES_OK = "[{\"login\":\"andypiper\",\"id\":552452,\"avatar_url\":\"https://avatars.githubusercontent.com/u/552452?v=3\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/andypiper\",\"html_url\":\"https://github.com/andypiper\",\"followers_url\":\"https://api.github.com/users/andypiper/followers\",\"following_url\":\"https://api.github.com/users/andypiper/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/andypiper/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/andypiper/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/andypiper/subscriptions\",\"organizations_url\":\"https://api.github.com/users/andypiper/orgs\",\"repos_url\":\"https://api.github.com/users/andypiper/repos\",\"events_url\":\"https://api.github.com/users/andypiper/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/andypiper/received_events\",\"type\":\"User\",\"site_admin\":false},{\"login\":\"tobyjaffey\",\"id\":281908,\"avatar_url\":\"https://avatars.githubusercontent.com/u/281908?v=3\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/tobyjaffey\",\"html_url\":\"https://github.com/tobyjaffey\",\"followers_url\":\"https://api.github.com/users/tobyjaffey/followers\",\"following_url\":\"https://api.github.com/users/tobyjaffey/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/tobyjaffey/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/tobyjaffey/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/tobyjaffey/subscriptions\",\"organizations_url\":\"https://api.github.com/users/tobyjaffey/orgs\",\"repos_url\":\"https://api.github.com/users/tobyjaffey/repos\",\"events_url\":\"https://api.github.com/users/tobyjaffey/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/tobyjaffey/received_events\",\"type\":\"User\",\"site_admin\":false}]";
    String RES_NOT_PARSABLE = "[{\"login\":\"andypiper\",\"id\":552452,\"avatar_url\":\"https://avatars.githubusercontent.com/u/552452?v=3\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/andypiper\",\"html_url\":\"https://github.com/andypiper\",\"followers_url\":\"https://api.github.com/users/andypiper/followers\",\"following_url\":\"https://api.github.com/users/andypiper/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/andypiper/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/andypiper/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/andypiper/subscriptions\",\"organizations_url\":\"https://api.github.com/users/andypiper/orgs\",\"repos_url\":\"https://api.github.com/users/andypiper/repos\",\"events_url\":\"https://api.github.com/users/andypiper/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/andypiper/received_events\",\"type\":\"User\",\"site_admin\":false},{\"login\":\"tobyjaffey\",\"id\":281908,\"avatar_url\":\"https://avatars.githubusercontent.com/u/281908?v=3\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/tobyjaffey\",\"html_url\":\"https://github.com/tobyjaffey\",\"followers_url\":\"https://api.github.com/users/tobyjaffey/followers\",\"following_url\":\"https://api.github.com/users/tobyjaffey/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/tobyjaffey/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/tobyjaffey/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/tobyjaffey/subscriptions\",\"organizations_url\":\"https://api.github.com/users/tobyjaffey/orgs\",\"repos_url\":\"https://api.github.com/users/tobyjaffey/repos\",\"events_url\":\"https://api.github.com/users/tobyjaffey/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/tobyjaffey/received_events\",\"type\":\"User\",\"site_admin\":false}";

    @Before
    public void setUp() throws Exception {

        presenter = new MainPresenterImpl(context);
        presenter.onTakeView(mainPresenter);
    }

    @Test
    @After
    public void responseOK() throws Exception {
        presenter.parseStargazerResult(200, RES_OK);
        assertNotEquals(presenter.getList(), null);
    }

    @Test
    @After
    public void responseNotParsable() throws Exception {
        presenter.parseStargazerResult(200, RES_NOT_PARSABLE);
        assertEquals(presenter.getList(), null);
    }

    @Test
    @After
    public void responseError500() throws Exception {
        presenter.parseStargazerResult(500, RES_OK);
        assertEquals(presenter.getList(), null);
    }

}