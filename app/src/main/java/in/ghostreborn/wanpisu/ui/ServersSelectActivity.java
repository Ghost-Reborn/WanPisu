package in.ghostreborn.wanpisu.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnimeServersAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.AllAnime;
import in.ghostreborn.wanpisu.utils.AnilistUtils;

public class ServersSelectActivity extends AppCompatActivity {

    public static RecyclerView animeServerSelectRecyclerView;
    int episodeNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servers_select);

        Intent intent = getIntent();
        String animeID = intent.getStringExtra("ANIME_ID");
        episodeNumber = intent.getIntExtra("ANIME_EPISODE_NUMBER", 1);

        animeServerSelectRecyclerView = findViewById(R.id.anime_server_select_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        animeServerSelectRecyclerView.setLayoutManager(linearLayoutManager);

        AnimeAsync animeAsync = new AnimeAsync(animeID, String.valueOf(episodeNumber));
        animeAsync.execute();

    }

    class AnimeAsync extends AsyncTask<String, Void, ArrayList<String>> {

        String animeID;
        String episodeNumber;

        public AnimeAsync(String mAnimeID, String mEpisodeNumber) {
            animeID = mAnimeID;
            episodeNumber = mEpisodeNumber;
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            String malID = String.valueOf(WanPisuConstants.ANIME_MAL_ID);
            String progress = String.valueOf(episodeNumber);
            String TOKEN = WanPisuConstants.preferences.getString(WanPisuConstants.WAN_PISU_ANILIST_TOKEN, "");
            AnilistUtils.saveAnimeProgress(malID, progress, TOKEN);

            return AllAnime.getAnimeServer(animeID, episodeNumber);
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);

            AnimeServersAdapter adapter = new AnimeServersAdapter(ServersSelectActivity.this, strings);
            animeServerSelectRecyclerView.setAdapter(adapter);

        }
    }

}