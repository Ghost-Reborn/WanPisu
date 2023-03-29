package in.ghostreborn.wanpisu.ui;

import static in.ghostreborn.wanpisu.utils.Anilist.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnilistAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.utils.Anilist;

public class AnilistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anilist);

        Anilist.checkAnilist(this);
        RecyclerView anilistRecyclerView = findViewById(R.id.anilist_recycler_view);
        ArrayList<String> anilists = new ArrayList<>();
        anilists.add("One Piece");
        anilists.add("Naruto");
        anilists.add("Dragon Ball");
        AnilistAdapter adapter = new AnilistAdapter(anilists);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        anilistRecyclerView.setLayoutManager(manager);
        anilistRecyclerView.setAdapter(adapter);

    }



}