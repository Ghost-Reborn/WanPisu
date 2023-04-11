package in.ghostreborn.wanpisu.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnimeEpisodesAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;

public class AnimeEpisodesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_episodes);

        String animeID = WanPisuConstants.wanPisus.get(WanPisuConstants.ANIME_INDEX)
                .getAnimeID();
        int episodes = WanPisuConstants.wanPisus.get(WanPisuConstants.ANIME_INDEX)
                .getTotalEpisodes();

        RecyclerView animeContainerView = findViewById(R.id.anime_episode_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 5);
        animeContainerView.setLayoutManager(gridLayoutManager);
        AnimeEpisodesAdapter adapter = new AnimeEpisodesAdapter(episodes, this, animeID);
        animeContainerView.setAdapter(adapter);

    }
}

