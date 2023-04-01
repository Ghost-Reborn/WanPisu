package in.ghostreborn.wanpisu.parser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import in.ghostreborn.wanpisu.model.Kitsu;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class KitsuAPI {

    private static final String TAG = "KitsuApi";
    private static final String AUTH_ENDPOINT = "https://kitsu.io/api/oauth/token";

    public static ArrayList<String> login(String username, String password) {
        ArrayList<String> loginData = new ArrayList<>();
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
            loginData.add(jsonObject.getString("access_token"));
        } catch (IOException e) {
            Log.e(TAG, "Error making API request", e);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            loginData.add(getUserID(loginData.get(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return loginData;
    }

    public static String getUserID(String TOKEN) throws Exception {
        String url = "https://kitsu.io/api/edge/users?filter[self]=true";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + TOKEN)
                .build();
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        JSONObject responseObject = new JSONObject(response.body().string());
        JSONArray dataArray = responseObject.getJSONArray("data");
        return ((JSONObject) dataArray.get(0))
                .getString("id");
    }

    public static ArrayList<Kitsu> getUserAnimeList(String TOKEN, int USER_ID) throws Exception {
        ArrayList<Kitsu> kitsus = new ArrayList<>();
        String url = "https://kitsu.io/api/edge/users/" + USER_ID + "/library-entries?include=anime&page[limit]=500";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + TOKEN)
                .build();
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        JSONArray included = new JSONObject(responseBody)
                .getJSONArray("included");
        for (int i = 0; i < included.length(); i++) {
            JSONObject attributes = included.getJSONObject(i)
                    .getJSONObject("attributes");
            String anime = attributes.getString("canonicalTitle");
            String thumbnail;
            if (!attributes.isNull("coverImage")) {
                thumbnail = attributes.getJSONObject("coverImage")
                        .getString("tiny");
            } else {
                thumbnail = attributes.getJSONObject("posterImage")
                        .getString("tiny");
            }
            kitsus.add(new Kitsu(anime, thumbnail));
        }
        return kitsus;
    }

}
