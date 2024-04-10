package in.ghostreborn.wanpisu.ui;

import android.graphics.Matrix;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.otaliastudios.zoom.ZoomImageView;
import com.otaliastudios.zoom.ZoomLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.Manga;
import in.ghostreborn.wanpisu.parser.MangaParser;

public class MangaActivity extends AppCompatActivity {

    int page = 0;
    boolean done = false;
    ArrayList<Manga> mangas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga);

        ImageView mangaImageView = findViewById(R.id.manga_image_view);

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            runOnUiThread(() -> {
                String url = mangas.get(0).getUrl();
                Picasso.get()
                        .load(url)
                        .into(mangaImageView);
                done = true;
            });
        };
        executor.execute(task);

        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(v -> {
            if (done){
                if (page<mangas.size()){
                    page++;
                    String url = mangas.get(page).getUrl();
                    Picasso.get()
                            .load(url)
                            .into(mangaImageView);
                }
            }
        });

        Button previousButton = findViewById(R.id.previous_button);
        previousButton.setOnClickListener(v -> {
            if (done){
                if (page>=0){
                    page--;
                    String url = mangas.get(page).getUrl();
                    Picasso.get()
                            .load(url)
                            .into(mangaImageView);
                }
            }
        });

    }


}