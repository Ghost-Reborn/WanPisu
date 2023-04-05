package in.ghostreborn.wanpisu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.adapter.AnimeDownloaderAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.Kitsu;
import in.ghostreborn.wanpisu.parser.AllAnime;

public class AnimeDownloaderActivity extends AppCompatActivity {

    RecyclerView animeDownloadRecyclerView;
    SearchView animeSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_downloader);

        animeDownloadRecyclerView = findViewById(R.id.anime_downloader_recycler_view);

        animeSearchView = findViewById(R.id.anime_downloader_search_view);
        animeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                new AnimeDownloadTask().execute();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    private class AnimeDownloadTask extends AsyncTask<Void, Void, ArrayList<Kitsu>> {

        @Override
        protected ArrayList<Kitsu> doInBackground(Void... voids) {
            WanPisuConstants.animeNames = new ArrayList<>();
            AllAnime.parseAnimeIDAnimeNameAnimeThumbnail(animeSearchView.getQuery().toString());
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Kitsu> kitsus) {
            AnimeDownloaderAdapter adapter = new AnimeDownloaderAdapter();
            LinearLayoutManager manager = new LinearLayoutManager(getBaseContext());
            animeDownloadRecyclerView.setLayoutManager(manager);
            animeDownloadRecyclerView.setAdapter(adapter);
        }
    }

}