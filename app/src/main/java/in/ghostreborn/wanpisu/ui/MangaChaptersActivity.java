package in.ghostreborn.wanpisu.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnimeEpisodesAdapter;
import in.ghostreborn.wanpisu.adapter.MangaChaptersAdapter;

public class MangaChaptersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_chapters);

        RecyclerView mangaChapterRecycler = findViewById(R.id.manga_chapter_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mangaChapterRecycler.setLayoutManager(manager);
        mangaChapterRecycler.setAdapter(new MangaChaptersAdapter());

    }
}