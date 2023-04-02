package in.ghostreborn.wanpisu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.adapter.KitsuAnimeDetailsAdapter;
import in.ghostreborn.wanpisu.model.KitsuDetails;
import in.ghostreborn.wanpisu.parser.KitsuAPI;

public class KitsuAnimeActivity extends AppCompatActivity {

    static String animeID;
    static TextView testText;
    static RecyclerView kitsuDetailsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitsu_anime);

        Intent intent = getIntent();
        animeID = intent.getStringExtra("ANIME_ID");

        kitsuDetailsRecyclerView = findViewById(R.id.kitsu_details_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        kitsuDetailsRecyclerView.setLayoutManager(manager);

        new KitsuAnimeAsyncTask().execute();

    }

    static class KitsuAnimeAsyncTask extends AsyncTask<Void, Void, ArrayList<KitsuDetails>> {

        @Override
        protected ArrayList<KitsuDetails> doInBackground(Void... voids) {
            return KitsuAPI.getAnimeDetails(animeID);
        }

        @Override
        protected void onPostExecute(ArrayList<KitsuDetails> s) {
            super.onPostExecute(s);
            KitsuAnimeDetailsAdapter adapter = new KitsuAnimeDetailsAdapter();
            kitsuDetailsRecyclerView.setAdapter(adapter);
        }
    }

}