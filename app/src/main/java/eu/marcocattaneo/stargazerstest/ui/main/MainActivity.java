package eu.marcocattaneo.stargazerstest.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import eu.marcocattaneo.stargazerstest.R;
import eu.marcocattaneo.stargazerstest.ui.general.BaseActivity;
import eu.marcocattaneo.stargazerstest.ui.main.MainPresenter;
import eu.marcocattaneo.stargazerstest.ui.main.MainPresenterImpl;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView stargazersList;
    private LinearLayoutManager mLayoutManager;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inti List
        stargazersList = (RecyclerView) findViewById(R.id.stargazersList);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        stargazersList.setLayoutManager(mLayoutManager);

        // Swipe to refresh
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_to_refreh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        presenter = new MainPresenterImpl();
        presenter.onTakeView(this);
    }

    @Override
    public void onRefresh() {
        presenter.refreshStagazers();
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();

        presenter.unsubscribe();
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
                presenter.showInputDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
