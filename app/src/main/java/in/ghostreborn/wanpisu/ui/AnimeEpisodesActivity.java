package in.ghostreborn.wanpisu.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnimeEpisodesAdapter;

public class AnimeEpisodesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_episodes);

        Intent intent = getIntent();
        String animeID = intent.getStringExtra("ANIME_ID");
        int episodes = intent.getIntExtra("ANIME_EPISODES", 0);

        RecyclerView animeContainerView = findViewById(R.id.anime_episode_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 5);
        animeContainerView.setLayoutManager(gridLayoutManager);
        AnimeEpisodesAdapter adapter = new AnimeEpisodesAdapter(episodes, this, animeID);
        animeContainerView.setAdapter(adapter);

    }
}

