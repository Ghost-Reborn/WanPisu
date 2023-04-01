package in.ghostreborn.wanpisu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class KitsuAnimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitsu_anime);

        Intent intent = getIntent();
        String animeID = intent.getStringExtra("ANIME_ID");
        Toast.makeText(this,animeID, Toast.LENGTH_SHORT).show();

    }
}