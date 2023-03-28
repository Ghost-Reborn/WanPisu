package in.ghostreborn.wanpisu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import in.ghostreborn.wanpisu.ui.AnilistLoginActivity;
import in.ghostreborn.wanpisu.ui.WanPisuActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView anilistTextView = findViewById(R.id.anilist_text_view);
        anilistTextView.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AnilistLoginActivity.class));
        });

        TextView watchAnimeTextView = findViewById(R.id.watch_anime_text_view);
        watchAnimeTextView.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, WanPisuActivity.class));
        });

    }

}