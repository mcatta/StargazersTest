package eu.marcocattaneo.stargazerstest.ui.main;

import android.content.Context;
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
import eu.marcocattaneo.stargazerstest.ui.general.BasePresenter;

public class MainPresenterImpl implements BasePresenter<MainPresenter> {

    private MainPresenter mainView;

    private Context context;

    private List<Stargazer> list;

    public MainPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void onTakeView(MainPresenter view) {
        this.mainView = view;
    }

    @Override
    public void onDetach() {
        this.mainView = null;
    }

    public void fetchData(GithubProfile githubProfile) {

        mainView.updateToolbar(githubProfile);

        String url = String.format("https://api.github.com/repos/%s/%s/stargazers", githubProfile.getUser(), githubProfile.getRepo());

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.get(url, new RequestCallback() {
            @Override
            public void onResult(int statusCode, String result) {
                parseStargazerResult(statusCode, result);
            }

            @Override
            public void onError(VolleyError error) {
                parseStargazerResult(error.networkResponse.statusCode, null);
                mainView.enableRefresh(false);
                error.printStackTrace();
            }
        });
        httpRequest.execute();

    }

    /**
     * Parse http result
     * @param statusCode
     * @param res
     */
    public boolean parseStargazerResult(int statusCode, @Nullable String res) {

        list = null;
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
                        list = stargazers;

                        mainView.refreshAdapter(stargazers);
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        mainView.showSnackbar(getString(R.string.connection_error));
                    }
                } else {
                    mainView.showSnackbar(getString(R.string.connection_error));
                }

                break;

            case 404:
                mainView.showSnackbar(getString(R.string.repository_not_found));
                mainView.showInputDialog();
                break;

            case -1:
            default:
                mainView.showSnackbar(getString(R.string.connection_error));
                break;

        }

        return false;

    }

    public List<Stargazer> getList() {
        return list;
    }

    public void refreshStagazers() {

        GithubProfile githubProfile = GithubProfileHelper.getInstance().get();

        if (githubProfile == null) {
            mainView.showInputDialog();
        } else {

            mainView.enableRefresh(true);
            fetchData(githubProfile);
        }

    }

    public String getString(@StringRes int resid) {
        return context.getString(resid);
    }

}
