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

        RecyclerView animeContainerView = findViewById(R.id.anime_episode_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        animeContainerView.setLayoutManager(manager);
        animeContainerView.setAdapter(new AnimeEpisodesAdapter(this));

        RecyclerView animeGroupContainerView = findViewById(R.id.anime_episode_group_recycler_view);
        GridLayoutManager groupManager = new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false);
        animeGroupContainerView.setLayoutManager(groupManager);
        animeGroupContainerView.setAdapter(new AnimeGroupAdapter(getPages(), animeContainerView));

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            for (int i = 0; i < WanPisuConstants.episodes.size(); i++) {
                String title = AllAnime.getEpisodeName(WanPisuConstants.ALL_ANIME_ID, WanPisuConstants.episodes.get(i).getEpisodeNumber());
                if (!title.isEmpty()){
                    WanPisuConstants.episodes.get(i).setEpisodeTitle(title);
                }
            }
            runOnUiThread(() -> {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                animeContainerView.setLayoutManager(linearLayoutManager);
                animeContainerView.setAdapter(new AnimeEpisodesAdapter(this));
            });
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

