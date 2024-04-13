package in.ghostreborn.wanpisu.ui;

import static java.security.AccessController.getContext;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnimeEpisodesAdapter;
import in.ghostreborn.wanpisu.adapter.AnimeGroupAdapter;
import in.ghostreborn.wanpisu.adapter.AnimeSearchAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.AllAnime;

public class AnimeEpisodesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_episodes);

        WanPisuConstants.ALL_ANIME_EPISODE_ADD = 0;
        WanPisuConstants.PAGE = 1;

        RecyclerView animeContainerView = findViewById(R.id.anime_episode_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        animeContainerView.setLayoutManager(manager);
        animeContainerView.setAdapter(new AnimeEpisodesAdapter(this));

        RecyclerView animeGroupContainerView = findViewById(R.id.anime_episode_group_recycler_view);
        GridLayoutManager groupManager = new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false);
        animeGroupContainerView.setLayoutManager(groupManager);
        animeGroupContainerView.setAdapter(new AnimeGroupAdapter(getPages(), animeContainerView, this));

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            int start = 0;
            int end = WanPisuConstants.EPISODE_VISIBLE;
            if (WanPisuConstants.episodes.size() < WanPisuConstants.EPISODE_VISIBLE){
                end = WanPisuConstants.episodes.size();
            }else {
                if (WanPisuConstants.PAGE > 1){
                    start = WanPisuConstants.PAGE * WanPisuConstants.EPISODE_VISIBLE;
                    if ((start + WanPisuConstants.EPISODE_VISIBLE) < WanPisuConstants.episodes.size()){
                        end = WanPisuConstants.episodes.size();
                    }else {
                        end = start = WanPisuConstants.EPISODE_VISIBLE;
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

    private int getPages() {
        int size = WanPisuConstants.episodes.size();
        if (size % WanPisuConstants.EPISODE_VISIBLE == 0) {
            return size / WanPisuConstants.EPISODE_VISIBLE;
        } else {
            return (int) (double) (size / WanPisuConstants.EPISODE_VISIBLE) + 1;
        }
    }

}

