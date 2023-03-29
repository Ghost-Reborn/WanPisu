package in.ghostreborn.wanpisu.ui;

import static in.ghostreborn.wanpisu.utils.Anilist.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.utils.Anilist;

public class AnilistActivity extends AppCompatActivity {

    static TextView userNameTextView;
    static String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anilist);

        userNameTextView = findViewById(R.id.user_name_text_view);
        Anilist.checkAnilist(this);

    }



}