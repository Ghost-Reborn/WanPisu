package in.ghostreborn.wanpisu.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnimeEpisodesAdapter;
import in.ghostreborn.wanpisu.adapter.AnimeGroupAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.WanPisu;

public class AnimeEpisodesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_episodes);

        RecyclerView animeContainerView = findViewById(R.id.anime_episode_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        animeContainerView.setLayoutManager(manager);
        animeContainerView.setAdapter(new AnimeEpisodesAdapter(this));

        RecyclerView animeGroupContainerView = findViewById(R.id.anime_episode_group_recycler_view);
        GridLayoutManager groupManager = new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false);
        animeGroupContainerView.setLayoutManager(groupManager);
        animeGroupContainerView.setAdapter(new AnimeGroupAdapter(getPages(), animeContainerView));

    }

    private int getPages() {
        return 100;
    }

}

