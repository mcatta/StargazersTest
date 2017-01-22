package eu.marcocattaneo.stargazerstest.business.helpers;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import eu.marcocattaneo.stargazerstest.data.GithubProfile;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class GithubProfileHelperTest {

    Context mMockContext;

    @Before
    public void setUp() {
        mMockContext = new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(), "test_");
        GithubProfileHelper.init(mMockContext);

        assertNotNull(GithubProfileHelper.getInstance());
    }

    @Test
    public void testSetAndGet() {
        GithubProfileHelper.getInstance().set("test", "test");

        GithubProfile profile = GithubProfileHelper.getInstance().get();
        assertNotNull(profile);

        assertEquals("test", profile.getUser());
        assertEquals("test", profile.getRepo());

    }

    @Test
    public void testReset() {
        GithubProfileHelper.getInstance().set("test", "test");

        assertNotNull(GithubProfileHelper.getInstance().get());
        GithubProfileHelper.getInstance().reset();
        assertNull(GithubProfileHelper.getInstance().get());

    }
}