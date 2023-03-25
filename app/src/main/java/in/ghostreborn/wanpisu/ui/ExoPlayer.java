package in.ghostreborn.wanpisu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import in.ghostreborn.wanpisu.R;

public class ExoPlayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);

        TextView test_text = findViewById(R.id.test_text);
        Intent intent = getIntent();
        int episode = intent.getIntExtra("ANIME_EPISODE_NUMBER", 0);
        test_text.setText(episode + "");

    }
}