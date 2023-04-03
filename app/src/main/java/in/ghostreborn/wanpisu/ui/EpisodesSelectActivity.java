package in.ghostreborn.wanpisu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnimeEpisodesAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;

public class EpisodesSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes_select);

        String animeID = WanPisuConstants.preferences
                .getString(WanPisuConstants.ALL_ANIME_ANIME_ID, "");
        int episodes = Integer.parseInt(WanPisuConstants.preferences
                .getString(WanPisuConstants.ALL_ANIME_ANIME_EPISODES, "0"));

        Log.e("EPISODES_SELECT", WanPisuConstants.preferences.getString(WanPisuConstants.ALL_ANIME_ANIME_ID, ""));

        RecyclerView animeContainerView = findViewById(R.id.anime_episode_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 5);
        animeContainerView.setLayoutManager(gridLayoutManager);
        AnimeEpisodesAdapter adapter = new AnimeEpisodesAdapter(episodes, this, animeID);
        animeContainerView.setAdapter(adapter);

    }
}

