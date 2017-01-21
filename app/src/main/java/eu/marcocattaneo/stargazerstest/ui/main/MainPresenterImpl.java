package eu.marcocattaneo.stargazerstest.ui.main;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import eu.marcocattaneo.stargazerstest.R;
import eu.marcocattaneo.stargazerstest.business.helpers.GithubProfileHelper;
import eu.marcocattaneo.stargazerstest.business.helpers.JSONMapper;
import eu.marcocattaneo.stargazerstest.business.http.HttpRequest;
import eu.marcocattaneo.stargazerstest.business.http.RequestCallback;
import eu.marcocattaneo.stargazerstest.data.GithubProfile;
import eu.marcocattaneo.stargazerstest.data.Stargazer;
import eu.marcocattaneo.stargazerstest.ui.adapter.StarGazerAdapter;
import eu.marcocattaneo.stargazerstest.ui.dialog.ChangeGithubProfileDialogFragment;

public class MainPresenterImpl implements MainPresenter {

    private MainActivity mainView;

    @Override
    public void onTakeView(MainActivity view) {
        this.mainView = view;
    }

    @Override
    public void onDetach() {
        this.mainView = null;
    }

    @Override
    public void subscribe() {
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void unsubscribe() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void fetchData(GithubProfile githubProfile) {

        String url = String.format("https://api.github.com/repos/%s/%s/stargazers", githubProfile.getUser(), githubProfile.getRepo());

        HttpRequest httpRequest = new HttpRequest(mainView);
        httpRequest.get(url, new RequestCallback() {
            @Override
            public void onResult(int statusCode, String result) {
                parseStargazerResult(statusCode, result);
            }

            @Override
            public void onError(VolleyError error) {
                mainView.mSwipeRefreshLayout.setRefreshing(false);
                error.printStackTrace();
                showSnackbar(error.getMessage());
            }
        });
        httpRequest.execute();
    }

    @Override
    public void refreshStagazers() {

        GithubProfile githubProfile = GithubProfileHelper.geInstance().get();

        if (githubProfile == null) {
            showInputDialog();
        } else {

            mainView.mSwipeRefreshLayout.setRefreshing(true);
            fetchData(githubProfile);
        }

    }

    /**
     * Parse http result
     * @param statusCode
     * @param res
     */
    public void parseStargazerResult(int statusCode, @Nullable String res) {

        switch (statusCode) {

            case 304:
            case 200:
            case 202:
                // OK
                if (res != null) {
                    Type listType = new TypeToken<ArrayList<Stargazer>>() {
                    }.getType();
                    try {
                        List<Stargazer> stargazers = JSONMapper.fromJson(res, listType);
                        fillAdapter(stargazers);
                    } catch (Exception e) {
                        showSnackbar(getString(R.string.connection_error));
                    }
                } else {
                    showSnackbar(getString(R.string.connection_error));
                }

                break;

            case -1:
            default:
                showSnackbar(getString(R.string.connection_error));
                break;

        }

    }

    /**
     * Put data on list
     * @param stargazers
     */
    private void fillAdapter(List<Stargazer> stargazers) {
        mainView.stargazersList.setAdapter(new StarGazerAdapter(stargazers));
        mainView.mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showInputDialog() {
        if (mainView.getSupportFragmentManager().findFragmentByTag(ChangeGithubProfileDialogFragment.TAG) != null)
            return;

        ChangeGithubProfileDialogFragment fragment = ChangeGithubProfileDialogFragment.newInstance();
        fragment.show(mainView.getSupportFragmentManager(), ChangeGithubProfileDialogFragment.TAG);
    }

    public void showSnackbar(String message) {
        Snackbar.make(mainView.mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    public String getString(@StringRes int resid) {
        return mainView.getString(resid);
    }

}
