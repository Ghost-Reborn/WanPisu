package in.ghostreborn.wanpisu.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnimeEpisodeGroupAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;

public class AnimeEpisodesActivity extends AppCompatActivity {

    public static RecyclerView animeContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_episodes);

        RecyclerView animeGroupEpisodeRecycler = findViewById(R.id.anime_episode_group_recycler_view);
        setArrayList();
        AnimeEpisodeGroupAdapter animeEpisodeGroupAdapter = new AnimeEpisodeGroupAdapter();
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        manager.setOrientation(GridLayoutManager.HORIZONTAL);
        animeGroupEpisodeRecycler.setLayoutManager(manager);
        animeGroupEpisodeRecycler.setAdapter(animeEpisodeGroupAdapter);
        animeContainerView = findViewById(R.id.anime_episode_recycler_view);
        new AnimeEpisodesAsync("1", AnimeEpisodesActivity.this).execute();

    }

    private void setArrayList() {
        WanPisuConstants.animeEpisodes = new ArrayList<>();
        int totalEpisodes = WanPisuConstants.wanPisus.get(WanPisuConstants.ANIME_INDEX)
                .getTotalEpisodes();
        int pages;
        int start = 0;
        if (totalEpisodes % 100 == 0) {
            pages = totalEpisodes / 100;
        } else {
            pages = (totalEpisodes / 100) + 1;
        }

        for (int i = 0; i < pages; i++) {
            if (i<pages-1){
                WanPisuConstants.animeEpisodes.add(
                        (start + 1) + " - " + (start + 100)
                );
            }else {
                WanPisuConstants.animeEpisodes.add(
                        (start + 1) + " - " + totalEpisodes
                );
            }
            start += 100;
        }

    }

}

