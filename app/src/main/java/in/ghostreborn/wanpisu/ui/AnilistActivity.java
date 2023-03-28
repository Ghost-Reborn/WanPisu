package in.ghostreborn.wanpisu.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.Anilist;

public class AnilistActivity extends AppCompatActivity {

    static TextView testText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anilist);

        testText = findViewById(R.id.test_text);

        SharedPreferences preferences = getSharedPreferences(WanPisuConstants.WAN_PISU_PREFERENCE, Context.MODE_PRIVATE);
        if (!preferences.contains(WanPisuConstants.WAN_PISU_ANILIST_TOKEN)){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Anilist.ANILIST_TOKEN_URL));
            startActivity(intent);
        }else {
            String token = preferences.getString(WanPisuConstants.WAN_PISU_ANILIST_TOKEN, "");
            AnilistAsync async = new AnilistAsync(token);
            async.execute();
        }

    }

    static class AnilistAsync extends AsyncTask<Void, Void, String> {

        String TOKEN;

        public AnilistAsync(String TOKEN){
            this.TOKEN = TOKEN;
        }

        @Override
        protected String doInBackground(Void... voids) {
            return Anilist.getAnilistUserName(TOKEN);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            testText.setText(s);
        }
    }

}