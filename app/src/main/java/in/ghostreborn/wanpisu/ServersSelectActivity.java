package in.ghostreborn.wanpisu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import in.ghostreborn.wanpisu.adapter.AnimeServersAdapter;
import in.ghostreborn.wanpisu.parser.AllAnime;
import in.ghostreborn.wanpisu.ui.ExoPlayerActivity;

public class ServersSelectActivity extends AppCompatActivity {

    public static RecyclerView animeServerSelectRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servers_select);

        Intent intent = getIntent();
        String animeID = intent.getStringExtra("ANIME_ID");
        int episodeNumber = intent.getIntExtra("ANIME_EPISODE_NUMBER", 1);

        animeServerSelectRecyclerView = findViewById(R.id.anime_server_select_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 5);
        animeServerSelectRecyclerView.setLayoutManager(gridLayoutManager);

        AnimeAsync animeAsync = new AnimeAsync(animeID, String.valueOf(episodeNumber));
        animeAsync.execute();

    }

    class AnimeAsync extends AsyncTask<String, Void, String> {

        String animeID;
        String episodeNumber;

        public AnimeAsync(String mAnimeID, String mEpisodeNumber) {
            animeID = mAnimeID;
            episodeNumber = mEpisodeNumber;
        }

        @Override
        protected String doInBackground(String... strings) {
            return AllAnime.getAnimeServer(animeID, episodeNumber);
        }

        @Override
        protected void onPostExecute(String strings) {
            super.onPostExecute(strings);

            AnimeServersAdapter adapter = new AnimeServersAdapter(ServersSelectActivity.this, strings);
            animeServerSelectRecyclerView.setAdapter(adapter);

        }
    }

}