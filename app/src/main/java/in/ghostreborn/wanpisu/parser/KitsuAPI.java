package in.ghostreborn.wanpisu.parser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.fragments.KitsuFragment;
import in.ghostreborn.wanpisu.model.Kitsu;
import in.ghostreborn.wanpisu.model.KitsuDetails;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class KitsuAPI {

    public static final String KITSU_API_BASE = "https://kitsu.io/api/edge/users/";
    public static final String KITSU_API_TAIL = "/library-entries?include=anime";
    private static final String TAG = "KitsuApi";
    private static final String AUTH_ENDPOINT = "https://kitsu.io/api/oauth/token";
    private static final String KITSU_SEARCH_API_BASE = "https://kitsu.io/api/edge/anime?filter[text]=";
    private static final String KITSU_SEARCH_API_TAIL = "&fields[anime]=id,canonicalTitle";

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

    public static ArrayList<Kitsu> getUserAnimeList(String TOKEN, String URL) throws Exception {
        Request request = new Request.Builder()
                .url(URL)
                .addHeader("Authorization", "Bearer " + TOKEN)
                .build();
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        Log.e("JSON_RESPONSE", responseBody);
        JSONObject responseObject = new JSONObject(responseBody);
        JSONArray library = responseObject.getJSONArray("data");
        JSONArray included = responseObject.getJSONArray("included");
        for (int i = 0; i < included.length(); i++) {
            JSONObject includedObject = included.getJSONObject(i);
            JSONObject libraryObject = library.getJSONObject(i);
            JSONObject attributes = includedObject
                    .getJSONObject("attributes");
            String animeID = includedObject.getString("id");
            String anime = attributes.getString("canonicalTitle");
            String description = attributes.getString("description");
            String thumbnail = attributes.getJSONObject("posterImage")
                    .getString("medium");
            String status = libraryObject.getJSONObject("attributes")
                                .getString("status");
            String progress =libraryObject.getJSONObject("attributes")
                    .getString("progress");
            String totalEpisodes = attributes.getString("episodeCount");
            String rating = attributes.getString("averageRating");
            WanPisuConstants.kitsus.add(new Kitsu(
                    animeID,
                    anime,
                    description,
                    thumbnail,
                    status,
                    progress,
                    totalEpisodes,
                    rating
            ));
        }

        JSONObject links = responseObject.getJSONObject("links");
        if (links.has("next")) {
            KitsuFragment.hasNext = true;
            KitsuFragment.nextURL = links.getString("next");
        } else {
            KitsuFragment.hasNext = false;
        }

        return WanPisuConstants.kitsus;
    }

    public static ArrayList<KitsuDetails> getAnimeDetails(String animeID) {

        OkHttpClient client = new OkHttpClient();
        WanPisuConstants.kitsuDetails = new ArrayList<>();

        String endpoint = "https://kitsu.io/api/edge/anime/" + animeID;

        Request request = new Request.Builder()
                .url(endpoint)
                .header("Content-Type", "application/json")
                .header("Accept", "application/vnd.api+json")
                .build();

        Log.e("ANIME_ID", animeID);

        Response response = null;

        try {
            response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();

                JSONObject jsonResponse = new JSONObject(responseBody);
                JSONObject attributes = jsonResponse.getJSONObject("data")
                        .getJSONObject("attributes");
                String animeTitle = attributes.getString("canonicalTitle");
                String thumbnail = attributes.getJSONObject("posterImage")
                        .getString("medium");
                String description = attributes.getString("description");
                String averageRating = attributes.getString("averageRating");
                String episodes = attributes.getString("episodeCount");
                WanPisuConstants.kitsuDetails.add(new KitsuDetails(
                        animeTitle,
                        thumbnail,
                        description,
                        averageRating,
                        episodes
                ));
            } else {
                Log.e("Error", "Unexpected response: " + response);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return WanPisuConstants.kitsuDetails;

    }

    public static String getAnimeCastings(String TOKEN){
        OkHttpClient client = new OkHttpClient();
        HttpUrl url = HttpUrl.parse("https://kitsu.io/api/edge/")
                .newBuilder()
                .addPathSegment("anime")
                .addPathSegment("20")
                .addQueryParameter("include", "person")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/vnd.api+json")
                .addHeader("Content-Type", "application/vnd.api+json")
                .addHeader("Authorization", "Bearer " + TOKEN)
                .build();
        Response response = null;
        String json = null;
        try {
            response = client.newCall(request).execute();
            json = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return json;

    }

    public static String searchAnime(String anime) {

        OkHttpClient client = new OkHttpClient();
        WanPisuConstants.kitsuDetails = new ArrayList<>();

        String endpoint = "https://kitsu.io/api/edge/anime?filter[text]=" + anime;

        Request request = new Request.Builder()
                .url(endpoint)
                .header("Content-Type", "application/json")
                .header("Accept", "application/vnd.api+json")
                .build();

        Response response = null;

        try {
            response = client.newCall(request).execute();
            String responseBody = response.body().string();
            Log.e("JSON_RESPONSE", responseBody);
            JSONObject responseObject = new JSONObject(responseBody);
            JSONArray data = responseObject.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject dataObject = data.getJSONObject(i);
                JSONObject attributes = dataObject
                        .getJSONObject("attributes");
                String animeID = dataObject.getString("id");
                String animeName = attributes.getString("canonicalTitle");
                String description = attributes.getString("description");
                String thumbnail = attributes.getJSONObject("posterImage")
                        .getString("medium");
                String status = dataObject.getJSONObject("attributes")
                        .getString("status");
                String totalEpisodes = attributes.getString("episodeCount");
                String rating = attributes.getString("averageRating");
                WanPisuConstants.kitsus.add(new Kitsu(
                        animeID,
                        animeName,
                        description,
                        thumbnail,
                        status,
                        "",
                        totalEpisodes,
                        rating
                ));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return "ERROR";

    }

    public static void getTrendingAnime() {
        String URL = "https://kitsu.io/api/edge/trending/anime?limit=50";

        try {
            Request request = new Request.Builder()
                    .url(new URL(URL))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/vnd.api+json")
                    .build();
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            Log.e("JSON_RESPONSE", responseBody);
            JSONObject responseObject = new JSONObject(responseBody);
            JSONArray data = responseObject.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject dataObject = data.getJSONObject(i);
                JSONObject attributes = dataObject
                        .getJSONObject("attributes");
                String animeID = dataObject.getString("id");
                String anime = attributes.getString("canonicalTitle");
                String description = attributes.getString("description");
                String thumbnail = attributes.getJSONObject("posterImage")
                        .getString("medium");
                String status = dataObject.getJSONObject("attributes")
                        .getString("status");
                String totalEpisodes = attributes.getString("episodeCount");
                String rating = attributes.getString("averageRating");
                WanPisuConstants.kitsus.add(new Kitsu(
                        animeID,
                        anime,
                        description,
                        thumbnail,
                        status,
                        "",
                        totalEpisodes,
                        rating
                ));
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }


}
