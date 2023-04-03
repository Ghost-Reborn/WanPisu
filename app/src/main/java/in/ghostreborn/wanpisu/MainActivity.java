package in.ghostreborn.wanpisu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import in.ghostreborn.wanpisu.parser.KitsuAPI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AnimeTest().execute();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if ((intent.getFlags() & Intent.FLAG_ACTIVITY_CLEAR_TOP) != 0) {
            finish();
        }

    }

    class AnimeTest extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return KitsuAPI.searchAnime("One Piece");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("ANIME_TAG", s);
        }
    }

}