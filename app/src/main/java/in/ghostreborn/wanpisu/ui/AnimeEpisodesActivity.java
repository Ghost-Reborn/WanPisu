package in.ghostreborn.wanpisu.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.MainActivity;
import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnimeEpisodeGroupAdapter;
import in.ghostreborn.wanpisu.adapter.AnimeEpisodesAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.JikanParser;

public class AnimeEpisodesActivity extends AppCompatActivity {

    public static RecyclerView animeContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_episodes);

        RecyclerView animeGroupEpisodeRecycler = findViewById(R.id.anime_episode_group_recycler_view);
        AnimeEpisodeGroupAdapter animeEpisodeGroupAdapter = new AnimeEpisodeGroupAdapter(
                WanPisuConstants.wanPisus.get(WanPisuConstants.ANIME_INDEX)
                        .getTotalEpisodes()
        );
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        manager.setOrientation(GridLayoutManager.HORIZONTAL);
        animeGroupEpisodeRecycler.setLayoutManager(manager);
        animeGroupEpisodeRecycler.setAdapter(animeEpisodeGroupAdapter);
        animeContainerView = findViewById(R.id.anime_episode_recycler_view);
        new AnimeEpisodesAsync("1", AnimeEpisodesActivity.this).execute();

    }

}

