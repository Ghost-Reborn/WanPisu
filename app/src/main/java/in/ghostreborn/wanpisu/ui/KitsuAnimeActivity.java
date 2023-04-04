package in.ghostreborn.wanpisu.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.Kitsu;
import in.ghostreborn.wanpisu.model.KitsuDetails;
import in.ghostreborn.wanpisu.parser.KitsuAPI;

public class KitsuAnimeActivity extends AppCompatActivity {

    static String animeID;
    static String animeIndex;
    static TextView kitsuDetailTextView;
    static TextView kitsuDetailEpisodesView;
    static TextView kitsuDetailStatusView;
    static TextView kitsuDetailRatingView;
    static TextView kitsuDetailProgressView;
    static TextView kitsuDetailDescriptionView;
    static ImageView kitsuDetailImageView;
    static Button kitsuDetailWatchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitsu_anime);

        animeID = WanPisuConstants.preferences
                .getString(WanPisuConstants.KITSU_ANIME_ID, "");
        animeIndex = WanPisuConstants.preferences
                .getString(WanPisuConstants.KITSU_ANIME_INDEX, "");

        kitsuDetailTextView = findViewById(R.id.kitsu_detail_text_view);
        kitsuDetailEpisodesView = findViewById(R.id.kitsu_detail_episodes_view);
        kitsuDetailStatusView = findViewById(R.id.kitsu_detail_status_view);
        kitsuDetailRatingView = findViewById(R.id.kitsu_detail_rating_view);
        kitsuDetailProgressView = findViewById(R.id.kitsu_detail_progress_view);
        kitsuDetailDescriptionView = findViewById(R.id.kitsu_detail_description_view);
        kitsuDetailImageView = findViewById(R.id.kitsu_detail_image_view);
        kitsuDetailWatchButton = findViewById(R.id.kitsu_detail_watch_button);

        new KitsuAnimeAsyncTask().execute();

    }

    class KitsuAnimeAsyncTask extends AsyncTask<Void, Void, ArrayList<KitsuDetails>> {

        @Override
        protected ArrayList<KitsuDetails> doInBackground(Void... voids) {
            KitsuAPI.getEpisodeDetails();
            return KitsuAPI.getAnimeDetails(animeID);
        }

        @Override
        protected void onPostExecute(ArrayList<KitsuDetails> s) {
            super.onPostExecute(s);
            Kitsu kitsu;
            if (WanPisuConstants.isUserAnime) {
                kitsu = WanPisuConstants.userKitsus.get(Integer.parseInt(animeIndex));
            } else {
                kitsu = WanPisuConstants.kitsus.get(Integer.parseInt(animeIndex));
            }
            kitsuDetailTextView.setText(kitsu.getAnime());
            kitsuDetailEpisodesView.setText(kitsu.getTotalEpisodes());
            kitsuDetailStatusView.setText(kitsu.getStatus());
            kitsuDetailRatingView.setText(kitsu.getRating());
            kitsuDetailProgressView.setText(kitsu.getProgress() + "/");
            kitsuDetailDescriptionView.setText(kitsu.getDescription());
            Picasso.get().load(kitsu.getThumbnail()).into(kitsuDetailImageView);
            kitsuDetailWatchButton.setOnClickListener(view -> {
                Intent watchIntent = new Intent(KitsuAnimeActivity.this, WanPisuActivity.class);
                WanPisuConstants.preferences.edit()
                        .putString(WanPisuConstants.KITSU_ANIME_NAME, kitsuDetailTextView.getText().toString())
                        .apply();
                startActivity(watchIntent);
            });
        }
    }

}