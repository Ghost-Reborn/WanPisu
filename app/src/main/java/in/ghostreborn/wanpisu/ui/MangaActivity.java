package in.ghostreborn.wanpisu.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AnimeSearchAdapter;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.Manga;
import in.ghostreborn.wanpisu.parser.AllAnime;
import in.ghostreborn.wanpisu.parser.MangaParser;

public class MangaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga);

        ImageView mangaImageView = findViewById(R.id.manga_image_view);

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            WanPisuConstants.wanPisus = new ArrayList<>();
            ArrayList<Manga> mangas = MangaParser.getManga("SFrub9DDGMrmdZWyh", "1");
            runOnUiThread(() -> {
                String url = mangas.get(0).getUrl();
                Picasso.get()
                        .load(url)
                        .into(mangaImageView);
            });
        };
        executor.execute(task);

    }
}