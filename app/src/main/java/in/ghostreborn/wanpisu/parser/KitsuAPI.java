package in.ghostreborn.wanpisu.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.Kitsu;
import in.ghostreborn.wanpisu.model.KitsuEpisode;
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
        } catch (IOException | JSONException e) {
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

    public static ArrayList<Kitsu> getUserAnimeList(String TOKEN, String URL) {
        try{
            Request request = new Request.Builder()
                    .url(URL)
                    .addHeader("Authorization", "Bearer " + TOKEN)
                    .build();
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            JSONObject responseObject = new JSONObject(responseBody);
            JSONArray library = responseObject.getJSONArray("data");
            JSONArray included = responseObject.getJSONArray("included");
            for (int i = 0; i < included.length(); i++) {
                JSONObject includedObject = included.getJSONObject(i);
                JSONObject libraryObject = library.getJSONObject(i);
                JSONObject attributes = includedObject
                        .getJSONObject("attributes");
                String animeID = includedObject.getString("id");
                String kitsuID = libraryObject.getString("id");
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
                WanPisuConstants.userKitsus.add(new Kitsu(
                        animeID,
                        kitsuID,
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
                WanPisuConstants.hasNext = true;
                WanPisuConstants.nextURL = links.getString("next");
            } else {
                WanPisuConstants.hasNext = false;
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return WanPisuConstants.userKitsus;
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
                        "",
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
                        "",
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

    public static void getEpisodeDetails(){
        String URL = "https://kitsu.io/api/edge/anime/" +
                WanPisuConstants.preferences.getString(WanPisuConstants.KITSU_ANIME_ID, "1") +
                "/episodes?fields[episodes]=number,canonicalTitle,thumbnail&page[limit]=20";

        WanPisuConstants.kitsuEpisodes = new ArrayList<>();

        try {
            Request request = new Request.Builder()
                    .url(new URL(URL))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/vnd.api+json")
                    .build();
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            JSONObject responseObject = new JSONObject(responseBody);
            JSONArray data = responseObject.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject dataObject = data.getJSONObject(i);
                JSONObject attributes = dataObject
                        .getJSONObject("attributes");
                String episodeNumber = attributes.getString("number");
                String title = attributes.getString("canonicalTitle");
                String thumbnail = attributes.getJSONObject("thumbnail")
                                .getString("original");
                WanPisuConstants.kitsuEpisodes.add(new KitsuEpisode(
                        episodeNumber,
                        title,
                        thumbnail
                ));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    public static boolean saveUserData(String ANIME_MEDIA_ID, String ANIME_STATUS, String ANIME_PROGRESS){

        String url = "https://kitsu.io/api/edge/library-entries/" + ANIME_MEDIA_ID;

        String accessToken = WanPisuConstants.preferences.getString(WanPisuConstants.KITSU_TOKEN, "");
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/vnd.api+json");

        String requestBody = "{\n"
                + "    \"data\": {\n"
                + "        \"type\": \"libraryEntries\",\n"
                + "        \"id\": \"" + ANIME_MEDIA_ID + "\",\n"
                + "        \"attributes\": {\n"
                + "            \"status\": \"" + ANIME_STATUS + "\",\n"
                + "            \"progress\": \"" + ANIME_PROGRESS + "\"\n"
                + "        }\n"
                + "    }\n"
                + "}";

        RequestBody body = RequestBody.create(requestBody, mediaType);

        Request request = new Request.Builder()
                .url(url)
                .patch(body)
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/vnd.api+json")
                .addHeader("Accept", "application/vnd.api+json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                return true;
            }else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }

}
