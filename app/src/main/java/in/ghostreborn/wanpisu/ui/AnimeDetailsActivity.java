package in.ghostreborn.wanpisu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.AllAnimeParser;

public class AnimeDetailsActivity extends AppCompatActivity {

    TextView animeDetailsTextView;
    ImageView animeDetailsImageView;
    Button animeDetailsWatchButton;
    TextView animeDetailsSynopsis;
    TextView animeDetailsPrequel;
    TextView animeDetailsSequel;

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

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            AllAnimeParser.parseAnimeByID(WanPisuConstants.ALL_ANIME_ID);
            runOnUiThread(() -> {
                animeDetailsTextView.setText("");
            });
        };
        executor.execute(task);

        animeDetailsWatchButton.setOnClickListener(view -> {
            startActivity(new Intent(AnimeDetailsActivity.this, AnimeEpisodesActivity.class));
        });

    }
}