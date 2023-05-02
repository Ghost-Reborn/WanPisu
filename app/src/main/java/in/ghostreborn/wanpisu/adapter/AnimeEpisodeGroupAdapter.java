package in.ghostreborn.wanpisu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.ui.AnimeEpisodesActivity;
import in.ghostreborn.wanpisu.ui.AnimeEpisodesAsync;

public class AnimeEpisodeGroupAdapter extends RecyclerView.Adapter<AnimeEpisodeGroupAdapter.ViewHolder> {

    int episodes = 0;
    int currentGroup = 0;

    public AnimeEpisodeGroupAdapter(int episodes) {
        this.episodes = episodes;
    }

    @NonNull
    @Override
    public AnimeEpisodeGroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anime_episode_group_list, parent, false);
        return new AnimeEpisodeGroupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeEpisodeGroupAdapter.ViewHolder holder, int position) {
        int lastEpisode;
        int pages;
        if (episodes % 100 == 0){
            pages =  episodes / 100;
        }else {
            int total = episodes / 100;
            pages =  total + 1;
        }
        if (position != pages - 1){
            lastEpisode = currentGroup + 100;
            holder.episodeGroupTextView.setText(
                    (currentGroup + 1) + " - " + (lastEpisode)
            );
        }else {
            lastEpisode = episodes;
            holder.episodeGroupTextView.setText(
                    (currentGroup + 1) + " - " + (lastEpisode)
            );
        }
        currentGroup += 100;

        holder.itemView.setOnClickListener(v -> {
            new AnimeEpisodesAsync(String.valueOf(position + 1), holder.itemView.getContext()).execute();
        });

    }

    @Override
    public int getItemCount() {
        if (episodes % 100 == 0){
            return episodes / 100;
        }else {
            int total = episodes / 100;
            return total + 1;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView episodeGroupTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            episodeGroupTextView = itemView.findViewById(R.id.anime_episode_group_text_view);
        }
    }

}

