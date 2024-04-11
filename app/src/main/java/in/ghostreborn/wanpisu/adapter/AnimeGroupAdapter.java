package in.ghostreborn.wanpisu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;

public class AnimeGroupAdapter extends RecyclerView.Adapter<AnimeGroupAdapter.ViewHolder> {

    int pages;
    RecyclerView recyclerView;
    public AnimeGroupAdapter(int pages, RecyclerView recyclerView){
        this.pages = pages;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public AnimeGroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anime_episode_group_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeGroupAdapter.ViewHolder holder, int position) {
        String episode = (position + 1) + "";
        holder.animeEpisodeGroupTextView.setText(episode);
        holder.itemView.setOnClickListener(v -> {
            WanPisuConstants.ALL_ANIME_EPISODE_ADD = holder.getAdapterPosition() * 100;
            LinearLayoutManager manager = new LinearLayoutManager(holder.itemView.getContext());
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(new AnimeEpisodesAdapter(holder.itemView.getContext()));
        });
    }

    @Override
    public int getItemCount() {
        return pages;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView animeEpisodeGroupTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            animeEpisodeGroupTextView = itemView.findViewById(R.id.anime_episode_group_text_view);
        }
    }
}