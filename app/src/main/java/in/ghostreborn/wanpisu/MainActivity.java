package in.ghostreborn.wanpisu;

import android.os.Bundle;

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
            ArrayList<WanPisu> animeDetailsArray = AllAnime.parseAnimeIDAnimeNameAnimeThumbnail("One Piece");
            AnimeSearchAdapter adapter = new AnimeSearchAdapter(animeDetailsArray);
            runOnUiThread(() -> animeContainerView.setAdapter(adapter));
        };
        executor.execute(task);
    }
}