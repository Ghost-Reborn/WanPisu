package in.ghostreborn.wanpisu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.Jikan;
import in.ghostreborn.wanpisu.model.WanPisu;
import in.ghostreborn.wanpisu.parser.JikanParser;
import in.ghostreborn.wanpisu.ui.AnimeEpisodesActivity;
import in.ghostreborn.wanpisu.ui.WanPisuActivity;

public class AnimeDetailsActivity extends AppCompatActivity {

    static TextView animeDetailsTextView;
    static ImageView animeDetailsImageView;
    static Button animeDetailsWatchButton;
    static TextView animeDetailsSynopsis;
    static TextView animeDetailsPrequel;
    static TextView animeDetailsSequel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_details);

        animeDetailsImageView = findViewById(R.id.anime_details_image_view);
        animeDetailsTextView = findViewById(R.id.anime_details_text_view);
        animeDetailsPrequel = findViewById(R.id.anime_details_prequel);
        animeDetailsSequel = findViewById(R.id.anime_details_sequel);
        animeDetailsWatchButton = findViewById(R.id.anime_details_watch_button);
        animeDetailsSynopsis = findViewById(R.id.anime_details_synopsis);

    }

    @Override
    protected void onResume() {
        super.onResume();
        new AnimeDetailsAsync().execute();
    }

    class AnimeDetailsAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            WanPisuConstants.jikans = new ArrayList<>();
            String malID = WanPisuConstants.wanPisus.get(WanPisuConstants.ANIME_INDEX).getMalID();
            JikanParser.parseAnimeFull(malID);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            Picasso.get().load(WanPisuConstants.jikans.get(0).getAnimeThumbnail())
                    .into(animeDetailsImageView);
            animeDetailsTextView.setText(WanPisuConstants.jikans.get(0).getAnimeTitle());
            animeDetailsSynopsis.setText(WanPisuConstants.jikans.get(0).getAnimeSynopsis());
            animeDetailsWatchButton.setOnClickListener(view -> {
                startActivity(new Intent(AnimeDetailsActivity.this, AnimeEpisodesActivity.class));
            });


        }
    }

}