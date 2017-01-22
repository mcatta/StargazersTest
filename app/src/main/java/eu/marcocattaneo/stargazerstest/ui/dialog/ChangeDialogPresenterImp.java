package eu.marcocattaneo.stargazerstest.ui.dialog;

import org.greenrobot.eventbus.EventBus;

import eu.marcocattaneo.stargazerstest.business.helpers.GithubProfileHelper;
import eu.marcocattaneo.stargazerstest.data.GithubProfile;

public class ChangeDialogPresenterImp implements ChangeDialogPresenter {

    private ChangeGithubProfileDialogFragment mainView;

    @Override
    public void onCheck() {
        mainView.positiveButton.setEnabled(
                !mainView.user.getText().toString().isEmpty() && !mainView.repo.getText().toString().isEmpty()
        );
    }

    @Override
    public void onSubmit() {
        GithubProfileHelper.getInstance().set(mainView.user.getText().toString(), mainView.repo.getText().toString());
        EventBus.getDefault().post(GithubProfileHelper.getInstance().get());
    }

    @Override
    public void fill() {
        GithubProfile githubProfile = GithubProfileHelper.getInstance().get();
        if (githubProfile != null) {
            mainView.user.setText(githubProfile.getUser());
            mainView.repo.setText(githubProfile.getRepo());
        }
    }

    @Override
    public void onTakeView(ChangeGithubProfileDialogFragment view) {
        mainView = view;
    }

    @Override
    public void onDetach() {
        mainView = null;
    }
}
