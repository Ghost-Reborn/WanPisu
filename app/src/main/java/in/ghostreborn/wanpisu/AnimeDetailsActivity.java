package in.ghostreborn.wanpisu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.parser.AllAnime;

public class AnimeDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_details);

        Intent intent = getIntent();
        String animeID = intent.getStringExtra("ANIME_ID");
        TextView testText = findViewById(R.id.test_text);
        testText.setText(animeID);
        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            ArrayList<String> servers = AllAnime.getAnimeServer(animeID);
            StringBuilder server = new StringBuilder();
            for (int i=0;i<servers.size();i++){
                server.append(servers.get(i));
                server.append("\n");
            }
            runOnUiThread(() -> testText.setText(server));
        };
        executor.execute(task);
    }
}