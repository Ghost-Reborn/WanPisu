package in.ghostreborn.wanpisu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.adapter.AnimeSearchAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView animeContainerView = findViewById(R.id.anime_container);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        animeContainerView.setLayoutManager(gridLayoutManager);
        ArrayList<String> animes = new ArrayList<>();
        animes.add("One Piece");
        animes.add("Bleach");
        animes.add("Naruto");
        animes.add("Dragon Ball Z");
        animes.add("Demon Slayer");
        AnimeSearchAdapter adapter = new AnimeSearchAdapter(animes);
        animeContainerView.setAdapter(adapter);

    }
}