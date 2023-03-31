package in.ghostreborn.wanpisu.parser;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Kitsu {

    private static final String TAG = "KitsuApi";
    private static final String AUTH_ENDPOINT = "https://kitsu.io/api/oauth/token";

    public static String login(String username, String password) {
        String accessToken = null;
        try {
            // Create HTTP client and request
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/vnd.api+json");
            RequestBody body = RequestBody.create(mediaType,
                    "{\"grant_type\":\"password\",\"username\":\"" + username +
                            "\",\"password\":\"" + password + "\"}");
            Request request = new Request.Builder()
                    .url(AUTH_ENDPOINT)
                    .post(body)
                    .addHeader("Content-Type", "application/vnd.api+json")
                    .addHeader("Accept", "application/vnd.api+json")
                    .build();

            // Send request and get response
            Response response = client.newCall(request).execute();
            String json = response.body().string();

            // Parse JSON response
            JSONObject jsonObject = new JSONObject(json);
            accessToken = jsonObject.getString("access_token");
        } catch (IOException e) {
            Log.e(TAG, "Error making API request", e);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return accessToken;
    }
}
