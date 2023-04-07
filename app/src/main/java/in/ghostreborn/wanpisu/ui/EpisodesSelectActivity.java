package in.ghostreborn.wanpisu.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnimeCategorizeAdapter;
import in.ghostreborn.wanpisu.adapter.AnimeEpisodesAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.Kitsu;
import in.ghostreborn.wanpisu.parser.KitsuAPI;

public class EpisodesSelectActivity extends AppCompatActivity {

    RecyclerView animeEpisodesRecycler;
    RecyclerView animeCategorizeRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes_select);

        animeEpisodesRecycler = findViewById(R.id.anime_episode_recycler_view);
        animeCategorizeRecycler = findViewById(R.id.episode_categorize_recycler_view);
        new KitsuEpisodeTask().execute();
        ImageView animeDownloadView = findViewById(R.id.anime_download_view);
        animeDownloadView.setOnClickListener(view -> {
            startActivity(new Intent(EpisodesSelectActivity.this, AnimeDownloaderActivity.class));
        });

    }

    private class KitsuEpisodeTask extends AsyncTask<Void, Void, ArrayList<Kitsu>> {

        @Override
        protected ArrayList<Kitsu> doInBackground(Void... voids) {
            KitsuAPI.getEpisodeDetails();
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Kitsu> kitsus) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            animeEpisodesRecycler.setLayoutManager(linearLayoutManager);
            AnimeEpisodesAdapter adapter = new AnimeEpisodesAdapter(
                    EpisodesSelectActivity.this,
                    WanPisuConstants.preferences
                            .getString(WanPisuConstants.ALL_ANIME_ANIME_ID, "")
            );
            animeEpisodesRecycler.setAdapter(adapter);
            AnimeCategorizeAdapter animeCategorizeAdapter = new AnimeCategorizeAdapter(
                    WanPisuConstants.preferences.getString(WanPisuConstants.ALL_ANIME_ANIME_EPISODES, "1")
            );
            GridLayoutManager manager = new GridLayoutManager(getBaseContext(), 1, GridLayoutManager.HORIZONTAL, false);
            animeCategorizeRecycler.setLayoutManager(manager);
            animeCategorizeRecycler.setAdapter(animeCategorizeAdapter);
        }
    }

}

