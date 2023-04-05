package in.ghostreborn.wanpisu;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.adapter.AnimeDownloaderAdapter;
import in.ghostreborn.wanpisu.model.AnimeDown;

public class AnimeDownloaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_downloader);

        ArrayList<AnimeDown> animeDowns = new ArrayList<>();
        animeDowns.add(new AnimeDown("One Piece"));
        animeDowns.add(new AnimeDown("One Piece2"));
        animeDowns.add(new AnimeDown("One Piece3"));
        animeDowns.add(new AnimeDown("One Piece4"));

        RecyclerView recyclerView = findViewById(R.id.anime_downloader_recycler_view);

        SearchView animeSearchView = findViewById(R.id.anime_downloader_search_view);
        animeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                AnimeDownloaderAdapter adapter = new AnimeDownloaderAdapter(animeDowns);
                LinearLayoutManager manager = new LinearLayoutManager(AnimeDownloaderActivity.this);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }
}