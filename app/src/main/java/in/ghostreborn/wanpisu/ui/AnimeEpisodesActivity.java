package in.ghostreborn.wanpisu.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.AnimeDetailsActivity;
import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnimeEpisodesAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.JikanParser;

public class AnimeEpisodesActivity extends AppCompatActivity {

    RecyclerView animeContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_episodes);

        animeContainerView = findViewById(R.id.anime_episode_recycler_view);
        new AnimeEpisodesAsync().execute();

    }

    class AnimeEpisodesAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String malID = WanPisuConstants.wanPisus.get(WanPisuConstants.ANIME_INDEX).getMalID();
            JikanParser.parseAnimeEpisodes(malID);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            String animeID = WanPisuConstants.wanPisus.get(WanPisuConstants.ANIME_INDEX)
                    .getAnimeID();
            int episodes = WanPisuConstants.wanPisus.get(WanPisuConstants.ANIME_INDEX)
                    .getTotalEpisodes();

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AnimeEpisodesActivity.this);
            animeContainerView.setLayoutManager(linearLayoutManager);
            AnimeEpisodesAdapter adapter = new AnimeEpisodesAdapter(episodes, AnimeEpisodesActivity.this, animeID);
            animeContainerView.setAdapter(adapter);


        }
    }

}

