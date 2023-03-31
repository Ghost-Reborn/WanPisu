package in.ghostreborn.wanpisu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.parser.Kitsu;

public class KitsuActivity extends AppCompatActivity {

    private static final String USERNAME = "GhostReborn";
    private static final String PASSWORD = "Androidjava@1#";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitsu);

        new LoginTask().execute();

    }

    private class LoginTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return Kitsu.login(USERNAME, PASSWORD);
        }

        @Override
        protected void onPostExecute(String accessToken) {
            if (accessToken != null) {
                Toast.makeText(KitsuActivity.this, accessToken, Toast.LENGTH_SHORT).show();
                // Store the access token or use it to make authenticated API requests
            } else {
                Toast.makeText(KitsuActivity.this, "failed to login", Toast.LENGTH_SHORT).show();
            }
        }
    }

}