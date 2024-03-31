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

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.ui.ExoPlayerActivity;

public class AnimeEpisodesAdapter extends RecyclerView.Adapter<AnimeEpisodesAdapter.ViewHolder> {

    int episodes = 0;
    Context context;
    String animeID;

    public AnimeEpisodesAdapter(int episodes, Context context, String animeID) {
        this.episodes = episodes;
        this.context = context;
        this.animeID = animeID;
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

        int correctPosition = position + 1;

        holder.episodeNumberTextView.setText(WanPisuConstants.jikanEpisodes.get(position).getEpisodeNumber());
        holder.episodeTitleTextView.setText(WanPisuConstants.jikanEpisodes.get(position).getEpisodeTitle());
        Picasso.get().load(WanPisuConstants.animeImageURL).into(holder.episodeImageView);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ExoPlayerActivity.class);
            intent.putExtra("ANIME_EPISODE_NUMBER", correctPosition + WanPisuConstants.ALL_ANIME_EPISODE_ADD);
            intent.putExtra("ANIME_ID", animeID);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return WanPisuConstants.jikanEpisodes.size();
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
