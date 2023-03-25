package in.ghostreborn.wanpisu;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.parser.AllAnime;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = "WANPISU";
    private TextView testText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        RecyclerView animeContainerView = findViewById(R.id.anime_container);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
//        animeContainerView.setLayoutManager(gridLayoutManager);
//        ArrayList<String> animes = new ArrayList<>();
//        animes.add("One Piece");
//        animes.add("Bleach");
//        animes.add("Naruto");
//        animes.add("Dragon Ball Z");
//        animes.add("Demon Slayer");
//        AnimeSearchAdapter adapter = new AnimeSearchAdapter(animes);
//        animeContainerView.setAdapter(adapter);

        testText = findViewById(R.id.test_text);

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            ArrayList<WanPisu> animeDetailsArray = AllAnime.parseAnimeIDAnimeNameAnimeThumbnail("One Piece");
            runOnUiThread(() -> {
                for (int i = 0; i < animeDetailsArray.size(); i++) {
                    testText.append(
                            animeDetailsArray.get(i).getAnimeID() +
                                    "\t" +
                                    animeDetailsArray.get(i).getAnimeName() +
                                    "\n"
                    );
                }
            });
        };
        executor.execute(task);
    }
}