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

    EditText animeEpisodeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_downloader);

        TextView animeName = findViewById(R.id.anime_download_text_view);
        TextView totalEpisodes = findViewById(R.id.anime_total_text_view);
        animeEpisodeEditText = findViewById(R.id.anime_episode_edit_text);
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
            String episodes = animeEpisodeEditText.getText().toString();

            int totalEpisodesToDownload = Integer.parseInt(episodes);
            for (int i=1; i<=totalEpisodesToDownload;i++){
                ArrayList<String> servers = AllAnime.getAnimeServer(WanPisuConstants.preferences.getString(WanPisuConstants.ALL_ANIME_ANIME_ID, ""), episodes);
                String srvr = "";
                for (String server : servers) {
                    if (server.contains("workfields")) {
                        srvr = server;
                    }
                }
                String animeName = WanPisuConstants.preferences
                        .getString(WanPisuConstants.ALL_ANIME_ANIME_NAME, "")
                        + " - " + i;
                AnimeDown animeDown = new AnimeDown(animeName, episodes, 0, srvr,false);
                WanPisuConstants.animeDowns.add(animeDown);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void server) {
        }
    }

}