package eu.marcocattaneo.stargazerstest.ui.main;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.VolleyError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import eu.marcocattaneo.stargazerstest.R;
import eu.marcocattaneo.stargazerstest.business.helpers.GithubProfileHelper;
import eu.marcocattaneo.stargazerstest.business.http.HttpRequest;
import eu.marcocattaneo.stargazerstest.business.http.RequestCallback;
import eu.marcocattaneo.stargazerstest.data.GithubProfile;
import eu.marcocattaneo.stargazerstest.data.Stargazer;
import eu.marcocattaneo.stargazerstest.ui.adapter.StarGazerAdapter;
import eu.marcocattaneo.stargazerstest.ui.dialog.ChangeGithubProfileDialogFragment;
import eu.marcocattaneo.stargazerstest.ui.general.BaseActivity;

public class MainActivity extends BaseActivity implements MainPresenter, SwipeRefreshLayout.OnRefreshListener {

    public CoordinatorLayout mCoordinatorLayout;

    public RecyclerView stargazersList;
    private LinearLayoutManager mLayoutManager;

    public SwipeRefreshLayout mSwipeRefreshLayout;

    public MainPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_main);

        // inti List
        stargazersList = (RecyclerView) findViewById(R.id.stargazersList);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        stargazersList.setLayoutManager(mLayoutManager);
        stargazersList.setAdapter(new StarGazerAdapter(null));

        // Swipe to refresh
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_to_refreh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        presenter = new MainPresenterImpl(this);
        presenter.onTakeView(this);
    }

    @Override
    public void onRefresh() {
        presenter.refreshStagazers();
    }

    @Override
    protected void onResume() {
        super.onResume();

        subscribe();

        // Get data
        presenter.refreshStagazers();
    }

    @Override
    protected void onPause() {
        super.onPause();

        unsubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.onDetach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.changeRepo:
                showInputDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

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
    public void enableRefresh(boolean enable) {
        mSwipeRefreshLayout.setRefreshing(enable);
    }

    @Override
    public void refreshAdapter(List<Stargazer> stargazers) {
        stargazersList.setAdapter(new StarGazerAdapter(stargazers));
        enableRefresh(false);
    }

    public void showSnackbar(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showInputDialog() {
        if (getSupportFragmentManager().findFragmentByTag(ChangeGithubProfileDialogFragment.TAG) != null)
            return;

        ChangeGithubProfileDialogFragment fragment = ChangeGithubProfileDialogFragment.newInstance();
        fragment.show(getSupportFragmentManager(), ChangeGithubProfileDialogFragment.TAG);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void fetchData(GithubProfile githubProfile) {
        presenter.fetchData(githubProfile);
    }

    public void updateToolbar(GithubProfile githubProfile) {
        getSupportActionBar().setTitle(githubProfile.getUser() + "/" + githubProfile.getRepo());
    }

}
