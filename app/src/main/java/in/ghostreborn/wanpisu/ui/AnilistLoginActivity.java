package in.ghostreborn.wanpisu.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.Anilist;

public class AnilistLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anilist_login);

        SharedPreferences preferences = getSharedPreferences(WanPisuConstants.WAN_PISU_PREFERENCE, Context.MODE_PRIVATE);
        if (preferences.contains(WanPisuConstants.WAN_PISU_ANILIST_TOKEN)){
            Toast.makeText(this,
                    preferences.getString(WanPisuConstants.WAN_PISU_ANILIST_TOKEN, ""),
                    Toast.LENGTH_SHORT
            ).show();
        }else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Anilist.ANILIST_TOKEN_URL));
            startActivity(intent);
        }

        EditText anilistTokenEditText = findViewById(R.id.anilist_token_edit_text);
        Button anilistTokenSubmitButton = findViewById(R.id.anilist_token_submit_button);
        anilistTokenSubmitButton.setOnClickListener(view -> {
            if (!anilistTokenEditText.getText().toString().equals("")){
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(WanPisuConstants.WAN_PISU_ANILIST_TOKEN, anilistTokenEditText.getText().toString());
                editor.apply();
            }
        });

    }
}