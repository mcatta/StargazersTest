package eu.marcocattaneo.stargazerstest.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import eu.marcocattaneo.stargazerstest.R;

public class StarGazerViewHolder extends RecyclerView.ViewHolder {

    public ImageView avatarImageView;

    public TextView userTextView;

    public StarGazerViewHolder(View itemView) {
        super(itemView);

        avatarImageView = (ImageView) itemView.findViewById(R.id.adapter_iv);
        userTextView = (TextView) itemView.findViewById(R.id.adapter_username);
    }
}
