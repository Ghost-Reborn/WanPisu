package in.ghostreborn.wanpisu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnimeEpisodesAdapter;

public class AnimeDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_details);

        Intent intent = getIntent();
        int episodes = intent.getIntExtra("ANIME_EPISODES", 0);

        RecyclerView animeContainerView = findViewById(R.id.anime_episode_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 5);
        animeContainerView.setLayoutManager(gridLayoutManager);
        AnimeEpisodesAdapter adapter = new AnimeEpisodesAdapter(episodes, this);
        animeContainerView.setAdapter(adapter);

    }
}

