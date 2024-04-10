package in.ghostreborn.wanpisu.parser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.WanPisu;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AllAnime {

    public static String parseByAllAnimeID(String allAnimeID){
        OkHttpClient client = new OkHttpClient();

        String baseUrl = "https://api.allanime.day/api";
        String queryUrl = baseUrl + "?variables=" + Uri.encode("{\"_id\":\"DdTHXmTjwHji2aApQ\"}") + "&query=" + Uri.encode("query($_id:String!){show(_id:$_id){" +
                "_id," +
                "availableEpisodesDetail" +
                "}}");

        Request request = new Request.Builder().url(queryUrl).header("Referer", "https://allanime.to").header("Cipher", "AES256-SHA256").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0").build();
        String rawJson = "NULL";

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                rawJson = response.body().string();
            }
        } catch (IOException e) {
            Log.e("TAG", e.getCause() + "");
        }

        return rawJson;

    }

    /**
     * If `anime` is null, returns recently updated anime
     */
    public static String getAnimeIDs(String anime){
        OkHttpClient client = new OkHttpClient();
        String baseUrl = "https://api.allanime.day/api";
        String queryUrl = baseUrl + "?variables=" +
                Uri.encode("{\"search\":{\"allowAdult\":false,\"allowUnknown\":false,\"query\":\"" + anime + "\"},\"limit\":39,\"page\":1,\"translationType\":\"sub\",\"countryOrigin\":\"ALL\"}") + "&query=" + Uri.encode("query($search:SearchInput,$limit:Int,$page:Int,$translationType:VaildTranslationTypeEnumType,$countryOrigin:VaildCountryOriginEnumType){shows(search:$search,limit:$limit,page:$page,translationType:$translationType,countryOrigin:$countryOrigin){edges{" +
                "_id" +
                "}}}");
        Request request = new Request.Builder().url(queryUrl).header("Referer", "https://allanime.to").header("Cipher", "AES256-SHA256").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0").build();
        String rawJson = "NULL";

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                rawJson = response.body().string();
            }
        } catch (IOException e) {
            Log.e("TAG", e.getCause() + "");
        }

        return rawJson;
    }

    public static ArrayList<String> getAnimeServer(String animeID, String episodeNumber) {
        String serverURL = "https://embed.ssbcontent.site/apivtwo/clock.json?id=" + decryptAllAnime(animeID, episodeNumber);

        ArrayList<String> servers = new ArrayList<>();
        try {

            URL url = new URL(serverURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            JSONObject baseJSON = new JSONObject(String.valueOf(response));
            JSONArray sourceURLs = baseJSON.
                    getJSONArray("links");
            JSONObject linkObject = sourceURLs.getJSONObject(0);
            String server = linkObject.getString("link");
            servers.add(server);


            return servers;
        } catch (JSONException | IOException e) {
            Log.e("TAG", String.format("%s", e.getMessage()));
        }

        return servers;

    }

    private static String decryptAllAnime(String showID, String episodeNumber) {

        // Connect and get encrypted url

        OkHttpClient client = new OkHttpClient();

        String baseUrl = "https://api.allanime.day/api";
        String queryUrl = baseUrl + "?variables=" + Uri.encode("{\"showId\":\"" + showID + "\",\"translationType\":\"sub\",\"episodeString\":\"" +
                episodeNumber +
                "\"}") + "&query=" + Uri.encode("query($showId:String!,$translationType:VaildTranslationTypeEnumType!,$episodeString:String!){episode(showId:$showId,translationType:$translationType,episodeString:$episodeString){episodeString,sourceUrls}}");

        Request request = new Request.Builder().url(queryUrl).header("Referer", "https://allanime.to").header("Cipher", "AES256-SHA256").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0").build();

        try (Response response = client.newCall(request).execute()) {
            String rawJSON;
            if (response.body() != null) {
                rawJSON = response.body().string();
                JSONObject jsonObject = new JSONObject(rawJSON);
                JSONArray sourceURLs = jsonObject.getJSONObject("data").getJSONObject("episode").getJSONArray("sourceUrls");

                for (int i = 0; i < sourceURLs.length(); i++) {
                    String decrypted = decryptAllAnimeServer(sourceURLs.getJSONObject(i).getString("sourceUrl").substring(2));
                    if (decrypted.contains("apivtwo")) {
                        decrypted = decrypted.substring(18);
                        return decrypted;
                    }
                }
            }

            return "NULL";

        } catch (IOException | JSONException e) {
            Log.e("TAG", e.getCause() + "");
        }

        return "NULL";

    }

    private static String decryptAllAnimeServer(String decrypt) {
        StringBuilder decryptedString = new StringBuilder();

        for (int i = 0; i < decrypt.length(); i += 2) {
            String hex = decrypt.substring(i, i + 2);
            int dec = Integer.parseInt(hex, 16);
            int xor = dec ^ 56;
            String oct = String.format("%03o", xor);
            char decryptedChar = (char) Integer.parseInt(oct, 8);
            decryptedString.append(decryptedChar);
        }

        return decryptedString.toString();
    }

    public static String getAllAnimeID(String anime, String malID) {

        OkHttpClient client = new OkHttpClient();

        String baseUrl = "https://api.allanime.day/api";
        String queryUrl = baseUrl + "?variables=" + Uri.encode("{\"search\":{\"allowAdult\":false,\"allowUnknown\":false,\"query\":\"" + anime + "\"},\"limit\":39,\"page\":1,\"translationType\":\"sub\",\"countryOrigin\":\"ALL\"}") + "&query=" + Uri.encode("query($search:SearchInput,$limit:Int,$page:Int,$translationType:VaildTranslationTypeEnumType,$countryOrigin:VaildCountryOriginEnumType){shows(search:$search,limit:$limit,page:$page,translationType:$translationType,countryOrigin:$countryOrigin){edges{" +
                "_id," +
                "malId," +
                "name," +
                "thumbnail," +
                "availableEpisodesDetail" +
                "}}}");

        Request request = new Request.Builder().url(queryUrl).header("Referer", "https://allanime.to").header("Cipher", "AES256-SHA256").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0").build();
        String rawJson = "NULL";

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                rawJson = response.body().string();
            }
        } catch (IOException e) {
            Log.e("TAG", e.getCause() + "");
        }

        try {
            JSONObject rawObject = new JSONObject(rawJson);
            JSONArray edgesArray = rawObject
                    .getJSONObject("data")
                    .getJSONObject("shows")
                    .getJSONArray("edges");

            for (int i = 0; i < edgesArray.length(); i++) {
                JSONObject edge = edgesArray.getJSONObject(i);
                String malId = edge.getString("malId");
                if (malId.equals(malID)) {
                    JSONArray availableEpisodesArray = edge.getJSONObject("availableEpisodesDetail")
                            .getJSONArray("sub");
                    ArrayList<String> tempArray = new ArrayList<>();
                    for (int j = 0; j < availableEpisodesArray.length(); j++) {
                        tempArray.add(availableEpisodesArray.getString(j));
                    }

                    // Reverse episodes array for Ascending order
                    ArrayList<String> episodesArray = new ArrayList<>();
                    for (int j = tempArray.size() - 1; j >= 0; j--) {
                        episodesArray.add(tempArray.get(j));
                    }


                    return edge.getString("_id");
                }
            }

            return edgesArray.toString();
        } catch (JSONException e) {
            Log.e("TAG", e.getCause() + "");
        }

        return rawJson;

    }

    public static String getAvailableEpisodes(String anime, String allAnimeID) {

        OkHttpClient client = new OkHttpClient();

        String baseUrl = "https://api.allanime.day/api";
        String queryUrl = baseUrl + "?variables=" + Uri.encode("{\"search\":{\"allowAdult\":false,\"allowUnknown\":false,\"query\":\"" + anime + "\"},\"limit\":39,\"page\":1,\"translationType\":\"sub\",\"countryOrigin\":\"ALL\"}") + "&query=" + Uri.encode("query($search:SearchInput,$limit:Int,$page:Int,$translationType:VaildTranslationTypeEnumType,$countryOrigin:VaildCountryOriginEnumType){shows(search:$search,limit:$limit,page:$page,translationType:$translationType,countryOrigin:$countryOrigin){edges{" +
                "_id," +
                "availableEpisodesDetail" +
                "}}}");

        Request request = new Request.Builder().url(queryUrl).header("Referer", "https://allanime.to").header("Cipher", "AES256-SHA256").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0").build();
        String rawJson = "NULL";

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                rawJson = response.body().string();
            }
        } catch (IOException e) {
            Log.e("TAG", e.getCause() + "");
        }

        try {
            JSONArray edgesArray = new JSONObject(rawJson)
                    .getJSONObject("data")
                    .getJSONObject("shows")
                    .getJSONArray("edges");
            for (int i = 0; i < edgesArray.length(); i++) {
                JSONObject edges = edgesArray.getJSONObject(i);
                String animeID = edges.getString("_id");
                if (animeID.equals(allAnimeID)) {
                    return edges.getJSONObject("availableEpisodesDetail")
                            .getString("sub");
                }
            }
        } catch (JSONException e) {
            Log.e("TAG", e.getCause() + "");
        }

        return null;

    }

}
