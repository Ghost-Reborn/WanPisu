package in.ghostreborn.wanpisu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.KitsuAnimeAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.Kitsu;
import in.ghostreborn.wanpisu.parser.KitsuAPI;

public class KitsuActivity extends AppCompatActivity {

    static RecyclerView kitsuRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitsu);

        kitsuRecyclerView = findViewById(R.id.kitsu_recycler_view);

        SharedPreferences preferences = getSharedPreferences(WanPisuConstants.WAN_PISU_PREFERENCE, MODE_PRIVATE);
        if (!preferences.contains(WanPisuConstants.KITSU_LOGIN_FINISHED)){
            startActivity(new Intent(KitsuActivity.this, KitsuLoginActivity.class));
        }else {
            new KitsuAnimeSearchTask().execute();
        }

    }

    private class KitsuAnimeSearchTask extends AsyncTask<Void, Void, ArrayList<Kitsu>> {

        @Override
        protected ArrayList<Kitsu> doInBackground(Void... voids) {
            SharedPreferences preferences = getSharedPreferences(WanPisuConstants.WAN_PISU_PREFERENCE, MODE_PRIVATE);
            String TOKEN = preferences.getString(WanPisuConstants.KITSU_TOKEN, "");
            String USER_ID = preferences.getString(WanPisuConstants.KITSU_USER_ID, "");
            ArrayList<Kitsu> kitsus = new ArrayList<>();
            Log.e("ANIME", USER_ID);
            try {
                String URL = KitsuAPI.KITSU_API_BASE + Integer.parseInt(USER_ID) + KitsuAPI.KITSU_API_TAIL;
                kitsus = KitsuAPI.getUserAnimeList(TOKEN, URL);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return kitsus;
        }

        @Override
        protected void onPostExecute(ArrayList<Kitsu> kitsus) {
            KitsuAnimeAdapter adapter = new KitsuAnimeAdapter();
            GridLayoutManager manager = new GridLayoutManager(getBaseContext(), 2);
            kitsuRecyclerView.setLayoutManager(manager);
            kitsuRecyclerView.setAdapter(adapter);
        }
    }

}