package in.ghostreborn.wanpisu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.Kitsu;

public class KitsuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitsu);

        SharedPreferences preferences = getSharedPreferences(WanPisuConstants.WAN_PISU_PREFERENCE, MODE_PRIVATE);
        if (!preferences.contains(WanPisuConstants.KITSU_LOGIN_FINISHED)){
            startActivity(new Intent(KitsuActivity.this, KitsuLoginActivity.class));
        }else {
            String TOKEN = preferences.getString(WanPisuConstants.KITSU_TOKEN, "");
            Toast.makeText(this, TOKEN, Toast.LENGTH_SHORT).show();
        }

    }

}