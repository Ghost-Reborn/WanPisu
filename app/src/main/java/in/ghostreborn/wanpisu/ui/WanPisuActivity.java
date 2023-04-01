package in.ghostreborn.wanpisu.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        Intent intent = getIntent();
        String animeName = intent.getStringExtra("ANIME_NAME");

        RecyclerView animeContainerView = findViewById(R.id.anime_container);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        animeContainerView.setLayoutManager(gridLayoutManager);

        // Get latest anime updates
        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            String anime = animeName == null ? "" : animeName;
            animeDetailsArray = AllAnime.parseAnimeIDAnimeNameAnimeThumbnail(anime);
            adapter = new AnimeSearchAdapter(WanPisuActivity.this);
            runOnUiThread(() -> animeContainerView.setAdapter(adapter));
        };
        executor.execute(task);

    }
}