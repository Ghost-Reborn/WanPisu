package in.ghostreborn.wanpisu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnimeSearchAdapter;
import in.ghostreborn.wanpisu.model.WanPisu;
import in.ghostreborn.wanpisu.parser.AllAnime;

public class WanPisuActivity extends AppCompatActivity {
    public static final String LOG_TAG = "WANPISU";

    public static ArrayList<WanPisu> animeDetailsArray;
    AnimeSearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wan_pisu);

        RecyclerView animeContainerView = findViewById(R.id.anime_container);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        animeContainerView.setLayoutManager(gridLayoutManager);

        // Get latest anime updates
        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            animeDetailsArray = AllAnime.parseAnimeIDAnimeNameAnimeThumbnail("");
            adapter = new AnimeSearchAdapter(WanPisuActivity.this);
            runOnUiThread(() -> animeContainerView.setAdapter(adapter));
        };
        executor.execute(task);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Runnable task = () -> {
                    animeDetailsArray = AllAnime.parseAnimeIDAnimeNameAnimeThumbnail(
                            searchView.getQuery().toString()
                    );
                    adapter = new AnimeSearchAdapter(WanPisuActivity.this);
                    runOnUiThread(() -> animeContainerView.setAdapter(adapter));
                };
                executor.execute(task);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }
}