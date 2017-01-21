package eu.marcocattaneo.stargazerstest.ui.general;

public interface BasePresenter<T> {

    void onTakeView(T view);

    void onDetach();

}
