package in.ghostreborn.wanpisu.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.Anilist;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AnilistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anilist);

        SharedPreferences preferences = getSharedPreferences(WanPisuConstants.WAN_PISU_PREFERENCE, Context.MODE_PRIVATE);
        if (!preferences.contains(WanPisuConstants.WAN_PISU_ANILIST_TOKEN)){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Anilist.ANILIST_TOKEN_URL));
            startActivity(intent);
        }else {
            String token = preferences.getString(WanPisuConstants.WAN_PISU_ANILIST_TOKEN, "");
            AnilistAsync async = new AnilistAsync(token);
            async.execute();
        }

    }

    static class AnilistAsync extends AsyncTask<Void, Void, Void>{

        private static final String TAG = "WanPisu";
        private static final String BASE_URL = "https://graphql.anilist.co";
        private static String ACCESS_TOKEN;

        public AnilistAsync(String token){
            ACCESS_TOKEN = token;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                OkHttpClient client = new OkHttpClient();
                HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();
                String url = urlBuilder.build().toString();

                JSONObject variables = new JSONObject();
                variables.put("status", "RELEASING");
                variables.put("sort", "POPULARITY_DESC");
                variables.put("perPage", 10);

                JSONObject query = new JSONObject();
                query.put("query", "query ($status: MediaStatus, $sort: [MediaSort], $perPage: Int) {\n" +
                        "  Page(page: 1, perPage: $perPage) {\n" +
                        "    media(status: $status, sort: $sort) {\n" +
                        "      id\n" +
                        "      title {\n" +
                        "        romaji\n" +
                        "        english\n" +
                        "        native\n" +
                        "      }\n" +
                        "      episodes\n" +
                        "      startDate {\n" +
                        "        year\n" +
                        "        month\n" +
                        "        day\n" +
                        "      }\n" +
                        "      endDate {\n" +
                        "        year\n" +
                        "        month\n" +
                        "        day\n" +
                        "      }\n" +
                        "      status\n" +
                        "      averageScore\n" +
                        "      genres\n" +
                        "      description\n" +
                        "    }\n" +
                        "  }\n" +
                        "}");
                query.put("variables", variables);

                Request request = new Request.Builder()
                        .url(url)
                        .post(okhttp3.RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), query.toString()))
                        .addHeader("Authorization", "Bearer " + ACCESS_TOKEN)
                        .build();

                Response response = client.newCall(request).execute();
                StringBuilder out = new StringBuilder();
                out.append(response.body().string());

                Log.e(TAG, out.toString());

                return null;

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}