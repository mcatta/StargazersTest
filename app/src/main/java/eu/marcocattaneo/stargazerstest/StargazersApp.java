package eu.marcocattaneo.stargazerstest;

import android.app.Application;

import eu.marcocattaneo.stargazerstest.business.helpers.GithubProfileHelper;

public class StargazersApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        GithubProfileHelper.init(this);
    }
}
