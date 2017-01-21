package eu.marcocattaneo.stargazerstest;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import eu.marcocattaneo.stargazerstest.ui.general.BaseActivity;

public class MainActivity extends BaseActivity {

    private RecyclerView stargazersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}
