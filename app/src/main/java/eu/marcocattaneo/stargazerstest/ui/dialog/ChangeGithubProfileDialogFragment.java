package eu.marcocattaneo.stargazerstest.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import eu.marcocattaneo.stargazerstest.R;
import eu.marcocattaneo.stargazerstest.ui.general.BaseDialogFragment;

public class ChangeGithubProfileDialogFragment extends BaseDialogFragment implements View.OnClickListener, TextWatcher {

    public static final String TAG = ChangeGithubProfileDialogFragment.class.getSimpleName();

    public static ChangeGithubProfileDialogFragment newInstance() {

        Bundle args = new Bundle();

        ChangeGithubProfileDialogFragment fragment = new ChangeGithubProfileDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ChangeDialogPresenter presenter;

    public Button positiveButton;

    public EditText user, repo;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog alert = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dial_add)
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                presenter.onSubmit();
                            }
                        }
                )
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                ).create();

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_input, null);

        alert.setView(view);

        user = (EditText) view.findViewById(R.id.input_user);
        repo = (EditText) view.findViewById(R.id.input_repo);
        user.addTextChangedListener(this);
        repo.addTextChangedListener(this);

        return alert;

    }

    @Override
    public void onStart() {
        super.onStart();

        positiveButton = (Button) getDialog().findViewById(android.R.id.button1);

        presenter = new ChangeDialogPresenterImp();
        presenter.onTakeView(this);

        presenter.fill();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case android.R.id.button1:
                presenter.onSubmit();
                break;

        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        presenter.onCheck();
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        presenter.onCheck();
    }

    @Override
    public void afterTextChanged(Editable editable) {
        presenter.onCheck();
    }
}
