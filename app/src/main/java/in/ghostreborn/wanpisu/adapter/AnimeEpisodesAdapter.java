package in.ghostreborn.wanpisu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;

public class AnimeEpisodesAdapter extends RecyclerView.Adapter<AnimeEpisodesAdapter.ViewHolder> {

    int episodes = 0;

    public AnimeEpisodesAdapter(int episodes){
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
        holder.episodeNumberTextView.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return episodes;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView episodeNumberTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            episodeNumberTextView = itemView.findViewById(R.id.episode_number_text_view);
        }
    }

}
