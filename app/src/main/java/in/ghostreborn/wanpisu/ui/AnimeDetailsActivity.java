package in.ghostreborn.wanpisu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;

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

        Picasso.get().load(WanPisuConstants.animeImageURL).into(animeDetailsImageView);
        animeDetailsWatchButton.setOnClickListener(view -> {
            startActivity(new Intent(AnimeDetailsActivity.this, AnimeEpisodesActivity.class));
        });

    }
}