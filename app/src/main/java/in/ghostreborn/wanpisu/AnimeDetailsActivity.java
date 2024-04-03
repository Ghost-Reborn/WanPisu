package in.ghostreborn.wanpisu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.JikanParser;
import in.ghostreborn.wanpisu.ui.AnimeEpisodesActivity;
import in.ghostreborn.wanpisu.utils.AnilistUtils;
import in.ghostreborn.wanpisu.utils.GraphQlRequestBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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