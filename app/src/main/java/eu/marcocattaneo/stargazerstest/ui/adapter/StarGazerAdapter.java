package eu.marcocattaneo.stargazerstest.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import eu.marcocattaneo.stargazerstest.R;
import eu.marcocattaneo.stargazerstest.data.Stargazer;

public class StarGazerAdapter extends RecyclerView.Adapter<StarGazerViewHolder> {

    private List<Stargazer> stargazers;

    public StarGazerAdapter(List<Stargazer> stargazers) {
        this.stargazers = stargazers == null ? new ArrayList<Stargazer>() : stargazers;
    }

    @Override
    public StarGazerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stargazer_adapter, parent, false);
        return new StarGazerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StarGazerViewHolder holder, int position) {

        Stargazer stargazer = getItem(position);

        holder.userTextView.setText(stargazer.getLogin());
    }

    @Override
    public int getItemCount() {
        return stargazers.size();
    }

    public Stargazer getItem(int position) {
        return stargazers.get(position);
    }
}
