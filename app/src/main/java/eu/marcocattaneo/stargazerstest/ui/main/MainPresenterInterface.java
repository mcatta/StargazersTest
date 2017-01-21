package eu.marcocattaneo.stargazerstest.ui.main;

import eu.marcocattaneo.stargazerstest.MainActivity;
import eu.marcocattaneo.stargazerstest.ui.general.BasePresenter;

public interface MainPresenterInterface extends BasePresenter<MainActivity> {

    void subscribe();

    void unsubscribe();

}
