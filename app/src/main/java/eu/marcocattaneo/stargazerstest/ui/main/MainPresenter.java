package eu.marcocattaneo.stargazerstest.ui.main;

import eu.marcocattaneo.stargazerstest.data.GithubProfile;
import eu.marcocattaneo.stargazerstest.ui.general.BasePresenter;

public interface MainPresenter extends BasePresenter<MainActivity> {

    void subscribe();

    void unsubscribe();

    void fetchData(GithubProfile githubProfile);

    void refreshStagazers();

    void showInputDialog();

}
