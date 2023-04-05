package in.ghostreborn.wanpisu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.KitsuAPI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WanPisuConstants.kitsus = new ArrayList<>();
        WanPisuConstants.kitsuEpisodes = new ArrayList<>();
        WanPisuConstants.preferences = getSharedPreferences(WanPisuConstants.WAN_PISU_PREFERENCE, MODE_PRIVATE);

        new SaveUserAsync().execute();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if ((intent.getFlags() & Intent.FLAG_ACTIVITY_CLEAR_TOP) != 0) {
            finish();
        }

    }

    class SaveUserAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return KitsuAPI.saveUserData();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("SAVE_DATA", s);
        }
    }

}