package in.ghostreborn.wanpisu.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.AllAnime;

public class AnimeGroupAdapter extends RecyclerView.Adapter<AnimeGroupAdapter.ViewHolder> {

    int pages;
    RecyclerView recyclerView;
    Activity activity;
    public AnimeGroupAdapter(int pages, RecyclerView recyclerView, Activity activity){
        this.pages = pages;
        this.recyclerView = recyclerView;
        this.activity = activity;
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
            WanPisuConstants.PAGE = position + 1;
            WanPisuConstants.ALL_ANIME_EPISODE_ADD = holder.getAdapterPosition() * WanPisuConstants.EPISODE_VISIBLE;
            getTitle(holder);
            LinearLayoutManager manager = new LinearLayoutManager(holder.itemView.getContext());
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(new AnimeEpisodesAdapter(holder.itemView.getContext()));
        });
    }

    private void getTitle(ViewHolder holder){
        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            int start = 0;
            int end = WanPisuConstants.EPISODE_VISIBLE;
            if (WanPisuConstants.episodes.size() < WanPisuConstants.EPISODE_VISIBLE){
                end = WanPisuConstants.episodes.size();
            }else {
                if (WanPisuConstants.PAGE > 1){
                    start = (WanPisuConstants.PAGE - 1) * WanPisuConstants.EPISODE_VISIBLE;
                    if ((start + WanPisuConstants.EPISODE_VISIBLE) > WanPisuConstants.episodes.size()){
                        end = WanPisuConstants.episodes.size();
                    }else {
                        end = WanPisuConstants.EPISODE_VISIBLE + start;
                    }
                }
            }
            while (start<end) {
                String title = AllAnime.getEpisodeName(WanPisuConstants.ALL_ANIME_ID, WanPisuConstants.episodes.get(start).getEpisodeNumber());
                if (!title.isEmpty()){
                    WanPisuConstants.episodes.get(start).setEpisodeTitle(title);
                }
                start++;
            }
        };
        executor.execute(task);
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