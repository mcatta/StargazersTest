package eu.marcocattaneo.stargazerstest.ui.main;

import java.util.List;

import eu.marcocattaneo.stargazerstest.data.GithubProfile;
import eu.marcocattaneo.stargazerstest.data.Stargazer;
import eu.marcocattaneo.stargazerstest.ui.general.BasePresenter;

public interface MainPresenter {

    void subscribe();

    void unsubscribe();

    void enableRefresh(boolean enable);

    void refreshAdapter(List<Stargazer> stargazers);

    void showInputDialog();

    void updateToolbar(GithubProfile profile);

    void showSnackbar(String message);

}
