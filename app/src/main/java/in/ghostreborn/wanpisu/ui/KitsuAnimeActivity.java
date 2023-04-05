package in.ghostreborn.wanpisu.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.Kitsu;
import in.ghostreborn.wanpisu.parser.KitsuAPI;

public class KitsuAnimeActivity extends AppCompatActivity {

    static String animeID;
    static String animeIndex;
    static TextView kitsuDetailTextView;
    static TextView kitsuDetailEpisodesView;
    static TextView kitsuDetailStatusView;
    static TextView kitsuDetailRatingView;
    static EditText kitsuDetailProgressView;
    static TextView kitsuDetailDescriptionView;
    static ImageView kitsuDetailImageView;
    static Button kitsuDetailWatchButton;
    static Button kitsuDetailUpdateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitsu_anime);

        animeID = WanPisuConstants.preferences
                .getString(WanPisuConstants.KITSU_ANIME_ID, "");
        animeIndex = WanPisuConstants.preferences
                .getString(WanPisuConstants.KITSU_ANIME_INDEX, "");

        findViews();
        setData();
    }

    private void findViews() {
        kitsuDetailTextView = findViewById(R.id.kitsu_detail_text_view);
        kitsuDetailEpisodesView = findViewById(R.id.kitsu_detail_episodes_view);
        kitsuDetailStatusView = findViewById(R.id.kitsu_detail_status_view);
        kitsuDetailRatingView = findViewById(R.id.kitsu_detail_rating_view);
        kitsuDetailProgressView = findViewById(R.id.kitsu_detail_progress_view);
        kitsuDetailDescriptionView = findViewById(R.id.kitsu_detail_description_view);
        kitsuDetailImageView = findViewById(R.id.kitsu_detail_image_view);
        kitsuDetailWatchButton = findViewById(R.id.kitsu_detail_watch_button);
        kitsuDetailUpdateButton = findViewById(R.id.kitsu_detail_update_button);
    }

    private void setData() {
        Kitsu kitsu;
        if (WanPisuConstants.isUserAnime) {
            kitsu = WanPisuConstants.userKitsus.get(Integer.parseInt(animeIndex));
        } else {
            kitsu = WanPisuConstants.kitsus.get(Integer.parseInt(animeIndex));
        }

        Log.e("ANIME_ID", kitsu.getAnimeID());

        String progress = kitsu.getProgress().equals("") ? "0" : kitsu.getProgress();

        kitsuDetailTextView.setText(kitsu.getAnime());
        kitsuDetailEpisodesView.setText("/" + kitsu.getTotalEpisodes());
        kitsuDetailStatusView.setText(kitsu.getStatus());
        kitsuDetailRatingView.setText(kitsu.getRating());
        kitsuDetailProgressView.setText(progress);
        kitsuDetailDescriptionView.setText(kitsu.getDescription());
        Picasso.get().load(kitsu.getThumbnail()).into(kitsuDetailImageView);
        kitsuDetailWatchButton.setOnClickListener(view -> {
            Intent watchIntent = new Intent(KitsuAnimeActivity.this, WanPisuActivity.class);
            WanPisuConstants.preferences.edit()
                    .putString(WanPisuConstants.KITSU_ANIME_NAME, kitsuDetailTextView.getText().toString())
                    .apply();
            startActivity(watchIntent);
        });
        kitsuDetailUpdateButton.setOnClickListener(view -> {
            new KitsuAnimeUpdate(
                    kitsu.getKitsuID(),
                    WanPisuConstants.KITSU_PROGRESS_CURRENT,
                    kitsuDetailProgressView.getText().toString()
            ).execute();
        });
    }

    class KitsuAnimeUpdate extends AsyncTask<Void, Void, Boolean> {

        String ANIME_MEDIA_ID, ANIME_STATUS, ANIME_PROGRESS;

        public KitsuAnimeUpdate(String ANIME_MEDIA_ID, String ANIME_STATUS, String ANIME_PROGRESS){
            this.ANIME_MEDIA_ID = ANIME_MEDIA_ID;
            this.ANIME_STATUS = ANIME_STATUS;
            this.ANIME_PROGRESS = ANIME_PROGRESS;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return KitsuAPI.saveUserData(ANIME_MEDIA_ID, ANIME_STATUS, ANIME_PROGRESS);
        }

        @Override
        protected void onPostExecute(Boolean saved) {
            super.onPostExecute(saved);
            if (saved){
                Toast.makeText(getBaseContext(), "Saved", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getBaseContext(), "Unable to Save", Toast.LENGTH_SHORT).show();
            }
        }
    }

}