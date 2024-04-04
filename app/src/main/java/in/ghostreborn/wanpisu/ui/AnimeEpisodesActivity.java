package in.ghostreborn.wanpisu.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnimeEpisodesAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;

public class AnimeEpisodesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_episodes);

        RecyclerView animeContainerView = findViewById(R.id.anime_episode_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        animeContainerView.setLayoutManager(manager);
        animeContainerView.setAdapter(new AnimeEpisodesAdapter(this, WanPisuConstants.wanPisu.getAvailableEpisodes()));

    }

}

