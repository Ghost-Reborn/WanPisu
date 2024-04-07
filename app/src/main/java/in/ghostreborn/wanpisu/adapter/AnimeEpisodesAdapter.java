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

        ArrayList<String> episodes = WanPisuConstants.wanPisu.getAvailableEpisodes();

        String episodeText = Integer.parseInt(episodes.get(position)) + WanPisuConstants.ALL_ANIME_EPISODE_ADD + "";

        holder.episodeNumberTextView.setText(episodeText);
        holder.episodeTitleTextView.setText(String.format("Episode %s", episodeText));
        Picasso.get().load(WanPisuConstants.wanPisu.getAnimeThumbnailUrl()).into(holder.episodeImageView);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ExoPlayerActivity.class);
            WanPisuConstants.ALL_ANIME_EPISODE_NUMBER = episodeText;
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if ((WanPisuConstants.ALL_ANIME_EPISODE_ADD + 100) < WanPisuConstants.wanPisu.getAvailableEpisodes().size()) {
            return 100;
        } else {
            int start = Integer.parseInt(WanPisuConstants.wanPisu.getAvailableEpisodes().get(0));
            if (start == 0) {
                return (WanPisuConstants.wanPisu.getAvailableEpisodes().size() % 100) - 1;
            }
            return WanPisuConstants.wanPisu.getAvailableEpisodes().size() % 100;
        }
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
