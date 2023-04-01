package in.ghostreborn.wanpisu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import in.ghostreborn.wanpisu.parser.KitsuAPI;

public class KitsuAnimeActivity extends AppCompatActivity {

    static String animeID;
    static TextView testText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitsu_anime);

        Intent intent = getIntent();
        animeID = intent.getStringExtra("ANIME_ID");
        testText = findViewById(R.id.kitsu_test_text);

        new KitsuAnimeAsyncTask().execute();

    }

    static class KitsuAnimeAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return KitsuAPI.getAnimeDetails(animeID);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            testText.setText(s);
        }
    }

}