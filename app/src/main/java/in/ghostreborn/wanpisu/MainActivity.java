package in.ghostreborn.wanpisu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.ui.AnilistActivity;
import in.ghostreborn.wanpisu.ui.KitsuActivity;
import in.ghostreborn.wanpisu.ui.WanPisuActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAnilistTokenFromIntentFilter();

        TextView anilistTextView = findViewById(R.id.anilist_text_view);
        anilistTextView.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AnilistActivity.class));
        });

        TextView watchAnimeTextView = findViewById(R.id.watch_anime_text_view);
        watchAnimeTextView.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, WanPisuActivity.class));
        });

        TextView kitsuTextView = findViewById(R.id.kitsu_text_view);
        kitsuTextView.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, KitsuActivity.class));
        });

    }

    private void getAnilistTokenFromIntentFilter(){
        SharedPreferences preferences = getSharedPreferences(WanPisuConstants.WAN_PISU_PREFERENCE, Context.MODE_PRIVATE);
        Intent urlIntent = getIntent();
        Uri data = urlIntent.getData();
        if (data != null){
            String url = data.toString();
            String token = url.substring(
                    url.indexOf("token=") + 6,
                    url.indexOf("&token_type")
            );
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(WanPisuConstants.WAN_PISU_ANILIST_TOKEN, token);
            editor.apply();
        }
    }

}