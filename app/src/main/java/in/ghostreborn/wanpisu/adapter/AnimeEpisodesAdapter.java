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
import in.ghostreborn.wanpisu.model.KitsuEpisode;
import in.ghostreborn.wanpisu.ui.ServersSelectActivity;

public class AnimeEpisodesAdapter extends RecyclerView.Adapter<AnimeEpisodesAdapter.ViewHolder> {

    Context context;
    String animeID;

    public AnimeEpisodesAdapter(Context context, String animeID) {
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

        KitsuEpisode kitsuEpisode = WanPisuConstants.kitsuEpisodes.get(position);
        holder.episodeNumberTextView.setText(kitsuEpisode.getEpisodeNumber());
        holder.episodeTitleTextView.setText(kitsuEpisode.getTitle());
        Picasso.get().load(kitsuEpisode.getThumbnail())
                        .into(holder.episodeNumberImageView);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ServersSelectActivity.class);
            WanPisuConstants.preferences.edit()
                            .putString(WanPisuConstants.ALL_ANIME_ANIME_EPISODE_NUMBER, String.valueOf(correctPosition))
                                    .apply();
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return WanPisuConstants.kitsuEpisodes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView episodeNumberTextView;
        public TextView episodeTitleTextView;
        public ImageView episodeNumberImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            episodeNumberTextView = itemView.findViewById(R.id.episode_number_text_view);
            episodeTitleTextView = itemView.findViewById(R.id.episode_title_text_view);
            episodeNumberImageView = itemView.findViewById(R.id.episode_number_image_view);
        }
    }

}
