package in.ghostreborn.wanpisu.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.KitsuAPI;

public class KitsuLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitsu_login);

        EditText kitsuUserNameTextView = findViewById(R.id.kitsu_user_name_text_view);
        EditText kitsuUserPassTextView = findViewById(R.id.kitsu_user_pass_text_view);
        Button kitsuLoginButton = findViewById(R.id.kitsu_login_button);
        Button kitsuSignUpButton = findViewById(R.id.kitsu_signup_button);
        kitsuLoginButton.setOnClickListener(view -> {
            String USERNAME = kitsuUserNameTextView.getText().toString().trim();
            String PASSWORD = kitsuUserPassTextView.getText().toString().trim();
            if (USERNAME.isEmpty() || PASSWORD.isEmpty()){
                return;
            }
            new KitsuLoginTask(
                    USERNAME,
                    PASSWORD
            ).execute();
        });
        kitsuSignUpButton.setOnClickListener(view -> {
            startActivity(
                    new Intent(Intent.ACTION_VIEW)
                            .setData(Uri.parse("https://kitsu.io/explore/anime")
                            )
            );
        });

    }

    private class KitsuLoginTask extends AsyncTask<Void, Void, ArrayList<String>> {

        String USERNAME, PASSWORD;

        public KitsuLoginTask(String USERNAME, String PASSWORD) {
            this.USERNAME = USERNAME;
            this.PASSWORD = PASSWORD;
        }

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            return KitsuAPI.login(USERNAME, PASSWORD);
        }

        @Override
        protected void onPostExecute(ArrayList<String> loginData) {
            if (loginData != null){
                SharedPreferences preferences = getSharedPreferences(WanPisuConstants.WAN_PISU_PREFERENCE, MODE_PRIVATE);
                preferences.edit()
                        .putBoolean(WanPisuConstants.KITSU_LOGIN_FINISHED, true)
                        .putString(WanPisuConstants.KITSU_TOKEN, loginData.get(0))
                        .putString(WanPisuConstants.KITSU_USER_ID, loginData.get(1))
                        .apply();
                finish();
            }
        }
    }


}