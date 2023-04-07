package in.ghostreborn.wanpisu.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.AnimeDown;
import in.ghostreborn.wanpisu.parser.AllAnime;

public class AnimeDownloaderActivity extends AppCompatActivity {

    EditText animeEpisodeStartEditText;
    EditText animeEpisodeEndEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_downloader);

        TextView animeName = findViewById(R.id.anime_download_text_view);
        TextView totalEpisodes = findViewById(R.id.anime_total_text_view);
        animeEpisodeStartEditText = findViewById(R.id.anime_episode_start_edit_text);
        animeEpisodeEndEditText = findViewById(R.id.anime_episode_end_edit_text);
        animeName.setText(WanPisuConstants.preferences.getString(WanPisuConstants.ALL_ANIME_ANIME_NAME, "0"));
        totalEpisodes.setText(WanPisuConstants.preferences.getString(WanPisuConstants.ALL_ANIME_ANIME_EPISODES, "0"));
        Button animeDownloadButton = findViewById(R.id.anime_download_button);
        animeDownloadButton.setOnClickListener(view -> {
            new AnimeDownloadTask().execute();
        });
        ImageView animeDownloadImageView = findViewById(R.id.anime_download_image_view);
        Picasso.get().load(WanPisuConstants.preferences.getString(WanPisuConstants.ALL_ANIME_ANIME_THUMBNAIL, "")).into(animeDownloadImageView);

    }

    private class AnimeDownloadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            int startEpisode = Integer.parseInt(animeEpisodeStartEditText.getText().toString());
            int endEpisode = Integer.parseInt(animeEpisodeEndEditText.getText().toString());

            for (int i=startEpisode; i<=endEpisode;i++){
                ArrayList<String> servers = AllAnime.getAnimeServer(WanPisuConstants.preferences.getString(WanPisuConstants.ALL_ANIME_ANIME_ID, ""), String.valueOf(startEpisode));
                String srvr = "";
                for (String server : servers) {
                    if (server.contains("workfields")) {
                        srvr = server;
                    }
                }
                String animeName = WanPisuConstants.preferences
                        .getString(WanPisuConstants.ALL_ANIME_ANIME_NAME, "")
                        + " - " + i;
                AnimeDown animeDown = new AnimeDown(animeName, startEpisode, 0, srvr,false);
                WanPisuConstants.animeDowns.add(animeDown);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void server) {
        }
    }

}