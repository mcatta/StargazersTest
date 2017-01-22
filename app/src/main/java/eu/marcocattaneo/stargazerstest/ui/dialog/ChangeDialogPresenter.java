package eu.marcocattaneo.stargazerstest.ui.dialog;

import eu.marcocattaneo.stargazerstest.ui.general.BasePresenter;

public interface ChangeDialogPresenter extends BasePresenter<ChangeGithubProfileDialogFragment> {

    void onCheck();

    void onSubmit();

    void fill();

}
