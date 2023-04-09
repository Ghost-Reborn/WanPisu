package in.ghostreborn.wanpisu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import in.ghostreborn.wanpisu.utils.GraphQlRequestBody;
import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.Anilist;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AnilistAnimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anilist_anime);

        TextView anilistAnimeName = findViewById(R.id.anilist_anime_details_text_view);
        ImageView anilistAnimeImage = findViewById(R.id.anilist_anime_details_image_view);
        Button anilistAnimeUpdate = findViewById(R.id.anilist_anime_details_update_button);
        EditText anilistAnimeProgress = findViewById(R.id.anilist_anime_details_edit_text);

        Anilist anilist = WanPisuConstants.anilists.get(WanPisuConstants.ANIME_INDEX);
        anilistAnimeName.setText(anilist.getAnimeName());
        Picasso.get().load(anilist.getAnimeImageUrl()).into(anilistAnimeImage);

        SharedPreferences preferences = getSharedPreferences(WanPisuConstants.WAN_PISU_PREFERENCE, MODE_PRIVATE);
        anilistAnimeUpdate.setOnClickListener(view -> {
            new AnilistAsync(
                    anilist.getAnilistID(),
                    anilistAnimeProgress.getText().toString(),
                    preferences.getString(WanPisuConstants.WAN_PISU_ANILIST_TOKEN, "")
                    ).execute();
        });

    }

    class AnilistAsync extends AsyncTask<Void, Void, Void>{

        String id;
        String progress;
        String ACCESS_TOKEN;

        public AnilistAsync(String id, String progress, String ACCESS_TOKEN){
            this.id = id;
            this.progress = progress;
            this.ACCESS_TOKEN = ACCESS_TOKEN;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            int animeId = 21; // The AniList ID of One Piece
            int newProgress = 64; // The updated progress value

            try {
                String query = "mutation SaveMediaListEntry($mediaId: Int!, $progress: Int!) {\n"
                        + "  SaveMediaListEntry(mediaId: $mediaId, progress: $progress) {\n"
                        + "    id\n"
                        + "    progress\n"
                        + "  }\n"
                        + "}";
                Map<String, Object> variables = new HashMap<>();
                variables.put("mediaId", animeId);
                variables.put("progress", newProgress);
                String requestBody = new Gson().toJson(new GraphQlRequestBody(query, variables));

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://graphql.anilist.co")
                        .header("Authorization", "Bearer " + ACCESS_TOKEN)
                        .post(RequestBody.create(MediaType.parse("application/json"), requestBody))
                        .build();

                Response response = client.newCall(request).execute();
                Log.e("TAG", response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}