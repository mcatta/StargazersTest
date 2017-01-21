package eu.marcocattaneo.stargazerstest.ui.main;

import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import eu.marcocattaneo.stargazerstest.MainActivity;
import eu.marcocattaneo.stargazerstest.data.StargazersTest;

public class MainPresenter implements MainPresenterInterface {

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(List<StargazersTest> stargazers) {

    }

    @Override
    public void unsubscribe() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }
}
