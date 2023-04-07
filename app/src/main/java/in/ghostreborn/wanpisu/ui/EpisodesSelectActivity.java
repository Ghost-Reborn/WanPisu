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

    public static RecyclerView animeEpisodesRecycler;
    public static RecyclerView animeCategorizeRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes_select);

        animeEpisodesRecycler = findViewById(R.id.anime_episode_recycler_view);
        animeCategorizeRecycler = findViewById(R.id.episode_categorize_recycler_view);
        new KitsuEpisodeTask("0").execute();
        ImageView animeDownloadView = findViewById(R.id.anime_download_view);
        animeDownloadView.setOnClickListener(view -> {
            startActivity(new Intent(EpisodesSelectActivity.this, AnimeDownloaderActivity.class));
        });

    }

    public static class KitsuEpisodeTask extends AsyncTask<Void, Void, ArrayList<Kitsu>> {

        String PAGE_OFFSET;

        public KitsuEpisodeTask(String PAGE_OFFSET){
            this.PAGE_OFFSET = PAGE_OFFSET;
        }

        @Override
        protected ArrayList<Kitsu> doInBackground(Void... voids) {
            KitsuAPI.getEpisodeDetails(PAGE_OFFSET);
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Kitsu> kitsus) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(animeCategorizeRecycler.getContext());
            animeEpisodesRecycler.setLayoutManager(linearLayoutManager);
            AnimeEpisodesAdapter adapter = new AnimeEpisodesAdapter(
                    animeEpisodesRecycler.getContext(),
                    WanPisuConstants.preferences
                            .getString(WanPisuConstants.ALL_ANIME_ANIME_ID, "")
            );
            animeEpisodesRecycler.setAdapter(adapter);
            AnimeCategorizeAdapter animeCategorizeAdapter = new AnimeCategorizeAdapter(
                    WanPisuConstants.preferences.getString(WanPisuConstants.ALL_ANIME_ANIME_EPISODES, "1")
            );
            GridLayoutManager manager = new GridLayoutManager(animeCategorizeRecycler.getContext(), 1, GridLayoutManager.HORIZONTAL, false);
            animeCategorizeRecycler.setLayoutManager(manager);
            animeCategorizeRecycler.setAdapter(animeCategorizeAdapter);
        }
    }

}

