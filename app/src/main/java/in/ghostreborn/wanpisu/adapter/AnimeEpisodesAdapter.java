package in.ghostreborn.wanpisu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.ui.ExoPlayerActivity;

public class AnimeEpisodesAdapter extends RecyclerView.Adapter<AnimeEpisodesAdapter.ViewHolder> {

    Context context;

    public AnimeEpisodesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anime_episodes_list, parent, false);
        return new AnimeEpisodesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String episode = WanPisuConstants.episodes.get(position);
        holder.episodeNumberTextView.setText(episode);
        holder.episodeTitleTextView.setText("Episode " + episode);
        Picasso.get()
                .load(WanPisuConstants.animeThumbnail)
                .into(holder.episodeImageView);
    }

    @Override
    public int getItemCount() {
        return WanPisuConstants.episodes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView episodeNumberTextView;
        public TextView episodeTitleTextView;
        public ImageView episodeImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            episodeNumberTextView = itemView.findViewById(R.id.episode_number_text_view);
            episodeTitleTextView = itemView.findViewById(R.id.episode_title_text_view);
            episodeImageView = itemView.findViewById(R.id.episode_image_view);
        }
    }

}
