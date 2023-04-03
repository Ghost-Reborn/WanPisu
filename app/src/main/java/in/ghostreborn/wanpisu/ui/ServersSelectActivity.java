package in.ghostreborn.wanpisu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnimeServersAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.AllAnime;

public class ServersSelectActivity extends AppCompatActivity {

    public static RecyclerView animeServerSelectRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servers_select);

        String animeID = WanPisuConstants.preferences
                .getString(WanPisuConstants.ALL_ANIME_ANIME_ID, "");
        int episodeNumber = Integer.parseInt(
                WanPisuConstants.preferences
                        .getString(WanPisuConstants.ALL_ANIME_ANIME_EPISODES, "0")
        );

        Log.e("SERVERS_ACTIVITY", WanPisuConstants.preferences.getString(WanPisuConstants.ALL_ANIME_ANIME_ID, ""));

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