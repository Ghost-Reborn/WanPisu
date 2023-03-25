package in.ghostreborn.wanpisu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AnimeDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_details);

        Intent intent = getIntent();
        TextView testText = findViewById(R.id.test_text);
        testText.setText(intent.getStringExtra("ANIME_ID"));

    }
}