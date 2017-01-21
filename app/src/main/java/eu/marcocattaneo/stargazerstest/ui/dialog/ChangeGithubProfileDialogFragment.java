package eu.marcocattaneo.stargazerstest.ui.dialog;

import android.os.Bundle;

import eu.marcocattaneo.stargazerstest.ui.general.BaseDialogFragment;

public class ChangeGithubProfileDialogFragment extends BaseDialogFragment {

    public static final String TAG = ChangeGithubProfileDialogFragment.class.getSimpleName();

    public static ChangeGithubProfileDialogFragment newInstance() {

        Bundle args = new Bundle();

        ChangeGithubProfileDialogFragment fragment = new ChangeGithubProfileDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
