package in.ghostreborn.wanpisu;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.adapter.AnimeSearchAdapter;
import in.ghostreborn.wanpisu.parser.AllAnime;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = "WANPISU";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView animeContainerView = findViewById(R.id.anime_container);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        animeContainerView.setLayoutManager(gridLayoutManager);

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            ArrayList<WanPisu> animeDetailsArray = AllAnime.parseAnimeIDAnimeNameAnimeThumbnail("");
            AnimeSearchAdapter adapter = new AnimeSearchAdapter(animeDetailsArray);
            runOnUiThread(() -> animeContainerView.setAdapter(adapter));
        };
        executor.execute(task);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Runnable task = () -> {
                    ArrayList<WanPisu> animeDetailsArray = AllAnime.parseAnimeIDAnimeNameAnimeThumbnail(
                            searchView.getQuery().toString()
                    );
                    AnimeSearchAdapter adapter = new AnimeSearchAdapter(animeDetailsArray);
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