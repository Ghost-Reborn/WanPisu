package in.ghostreborn.wanpisu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.ui.AnimeEpisodesAsync;

public class AnimeEpisodeGroupAdapter extends RecyclerView.Adapter<AnimeEpisodeGroupAdapter.ViewHolder> {

    @NonNull
    @Override
    public AnimeEpisodeGroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anime_episode_group_list, parent, false);
        return new AnimeEpisodeGroupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeEpisodeGroupAdapter.ViewHolder holder, int position) {
        holder.episodeGroupTextView.setText(WanPisuConstants.animeEpisodes.get(position));

        holder.itemView.setOnClickListener(v -> {
            new AnimeEpisodesAsync(String.valueOf(position + 1), holder.itemView.getContext()).execute();
        });

    }

    @Override
    public int getItemCount() {
        return WanPisuConstants.animeEpisodes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView episodeGroupTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            episodeGroupTextView = itemView.findViewById(R.id.anime_episode_group_text_view);
        }
    }

}

