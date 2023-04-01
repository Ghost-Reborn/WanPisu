package in.ghostreborn.wanpisu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.Kitsu;

public class KitsuActivity extends AppCompatActivity {

    static TextView testText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitsu);

        testText = findViewById(R.id.test_text);

        SharedPreferences preferences = getSharedPreferences(WanPisuConstants.WAN_PISU_PREFERENCE, MODE_PRIVATE);
        if (!preferences.contains(WanPisuConstants.KITSU_LOGIN_FINISHED)){
            startActivity(new Intent(KitsuActivity.this, KitsuLoginActivity.class));
        }else {
            new KitsuAnimeSearchTask().execute();
        }



    }

    private class KitsuAnimeSearchTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            SharedPreferences preferences = getSharedPreferences(WanPisuConstants.WAN_PISU_PREFERENCE, MODE_PRIVATE);
            String TOKEN = preferences.getString(WanPisuConstants.KITSU_TOKEN, "");
            try {
                return Kitsu.getAnime(TOKEN, 12);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String response) {
            testText.setText(response);
        }
    }

}