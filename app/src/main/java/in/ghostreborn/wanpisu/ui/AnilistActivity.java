package in.ghostreborn.wanpisu.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnilistAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.Anilist;
import in.ghostreborn.wanpisu.parser.AnilistParser;
import in.ghostreborn.wanpisu.utils.AnilistUtils;

public class AnilistActivity extends AppCompatActivity {

    String userName;
    String TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anilist);

        AnilistUtils.checkAnilist(this);

        SharedPreferences preferences = getSharedPreferences(WanPisuConstants.WAN_PISU_PREFERENCE, MODE_PRIVATE);
        TOKEN = preferences.getString(WanPisuConstants.WAN_PISU_ANILIST_TOKEN, "");
        userName = preferences.getString(WanPisuConstants.ANILIST_USER_NAME, "");

        RecyclerView anilistRecyclerView = findViewById(R.id.anilist_recycler_view);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        anilistRecyclerView.setLayoutManager(manager);

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            ArrayList<Anilist> anilists = AnilistParser.getAnimeDetails(userName, WanPisuConstants.ANIME_CURRENT, TOKEN);
            AnilistAdapter adapter = new AnilistAdapter(anilists);
            runOnUiThread(() -> {
                anilistRecyclerView.setAdapter(adapter);
            });
        };
        executor.execute(task);

    }



}