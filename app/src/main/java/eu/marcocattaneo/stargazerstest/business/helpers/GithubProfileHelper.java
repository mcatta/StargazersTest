package eu.marcocattaneo.stargazerstest.business.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import eu.marcocattaneo.stargazerstest.data.GithubProfile;

public class GithubProfileHelper {

    private static final String PREF_USER = "eu.marcocattaneo.stargazerstest.data.GithubProfile.user";

    private static final String PREF_REPO = "eu.marcocattaneo.stargazerstest.data.GithubProfile.pass";

    private static GithubProfileHelper singleThon;

    public static void init(Context context) {
        singleThon = new GithubProfileHelper(context);
    }

    public static GithubProfileHelper geInstance() {
        return singleThon;
    }

    private Context mContext;

    private SharedPreferences sharedPreferences;

    private GithubProfileHelper(Context context) {
        mContext = context;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public @Nullable GithubProfile get() {
        String user = sharedPreferences.getString(PREF_USER, null);
        String repo = sharedPreferences.getString(PREF_REPO, null);

        return user != null && repo != null ? new GithubProfile(user, repo) : null;
    }

    public boolean set(@NonNull String username, @NonNull String repoName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_USER, username);
        editor.putString(PREF_REPO, repoName);

        return editor.commit();
    }

}
