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

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.WanPisu;
import in.ghostreborn.wanpisu.ui.ExoPlayerActivity;

public class AnimeEpisodesAdapter extends RecyclerView.Adapter<AnimeEpisodesAdapter.ViewHolder> {

    Context context;
    ArrayList<String> episodes;
    public AnimeEpisodesAdapter (Context context, ArrayList<String> episodes){
        this.context = context;
        this.episodes = episodes;
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

        holder.episodeNumberTextView.setText(episodes.get(position));
        holder.episodeTitleTextView.setText("Episode " + episodes.get(position));
        Picasso.get().load(WanPisuConstants.wanPisu.getAnimeThumbnailUrl()).into(holder.episodeImageView);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ExoPlayerActivity.class);
            WanPisuConstants.ALL_ANIME_EPISODE_NUMBER = episodes.get(position);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return WanPisuConstants.wanPisu.getAvailableEpisodes().size();
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
