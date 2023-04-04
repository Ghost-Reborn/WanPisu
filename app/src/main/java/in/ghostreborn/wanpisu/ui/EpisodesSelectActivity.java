package in.ghostreborn.wanpisu.ui;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnimeEpisodesAdapter;
import in.ghostreborn.wanpisu.adapter.KitsuAnimeAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.Kitsu;
import in.ghostreborn.wanpisu.parser.KitsuAPI;

public class EpisodesSelectActivity extends AppCompatActivity {

    RecyclerView animeContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes_select);

        animeContainerView = findViewById(R.id.anime_episode_recycler_view);
        new KitsuEpisodeTask().execute();

    }

    private class KitsuEpisodeTask extends AsyncTask<Void, Void, ArrayList<Kitsu>> {

        @Override
        protected ArrayList<Kitsu> doInBackground(Void... voids) {
            KitsuAPI.getEpisodeDetails();
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Kitsu> kitsus) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 5);
            animeContainerView.setLayoutManager(gridLayoutManager);
            AnimeEpisodesAdapter adapter = new AnimeEpisodesAdapter(
                    Integer.parseInt(WanPisuConstants.preferences
                            .getString(WanPisuConstants.ALL_ANIME_ANIME_EPISODES, "0")),
                    EpisodesSelectActivity.this,
                    WanPisuConstants.preferences
                            .getString(WanPisuConstants.ALL_ANIME_ANIME_ID, "")
            );
            animeContainerView.setAdapter(adapter);
        }
    }

}

