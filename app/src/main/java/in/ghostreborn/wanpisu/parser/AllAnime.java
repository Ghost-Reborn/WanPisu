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
import in.ghostreborn.wanpisu.model.Anilist;
import in.ghostreborn.wanpisu.model.WanPisu;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AllAnime {

    /**
     * JSON array with `edges` provides anime details
     *
     * @return ArrayList<WanPisu>
     */
    public static ArrayList<WanPisu> parseAnimeIDAnimeNameAnimeThumbnail(String anime) {

        OkHttpClient client = new OkHttpClient();

        String baseUrl = "https://api.allanime.day/api";
        String queryUrl = baseUrl + "?variables=" + Uri.encode("{\"search\":{\"allowAdult\":false,\"allowUnknown\":false,\"query\":\"" + anime + "\"},\"limit\":39,\"page\":1,\"translationType\":\"sub\",\"countryOrigin\":\"ALL\"}") + "&query=" + Uri.encode("query($search:SearchInput,$limit:Int,$page:Int,$translationType:VaildTranslationTypeEnumType,$countryOrigin:VaildCountryOriginEnumType){shows(search:$search,limit:$limit,page:$page,translationType:$translationType,countryOrigin:$countryOrigin){edges{" +
                "_id," +
                "name," +
                "malId," +
                "thumbnail," +
                "availableEpisodesDetail" +
                "}}}");

        Request request = new Request.Builder().url(queryUrl).header("Referer", "https://allanime.to").header("Cipher", "AES256-SHA256").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0").build();
        String rawJson = "NULL";

        try(Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                rawJson = response.body().string();
            }
            Log.e("TAG", rawJson);
        } catch (IOException e) {
            Log.e("TAG", e.getCause() + "");
        }


        WanPisuConstants.wanPisus = new ArrayList<>();
        try {
            JSONArray edgesArray = new JSONObject(rawJson)
                    .getJSONObject("data")
                    .getJSONObject("shows")
                    .getJSONArray("edges");
            for (int i = 0; i < edgesArray.length(); i++) {
                JSONObject edges = edgesArray.getJSONObject(i);
                String animeID = edges.getString("_id");
                String malID = edges.getString("malId");
                String animeName = edges.getString("name");
                String animeThumbnailUrl = edges.getString("thumbnail");
                JSONArray availableEpisodesArray = edges.getJSONObject("availableEpisodesDetail")
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

                WanPisuConstants.wanPisus.add(new WanPisu(
                        animeID,
                        animeName,
                        animeThumbnailUrl,
                        malID,
                        episodesArray
                ));
            }
        } catch (JSONException e) {
            Log.e("TAG", e.getCause() + "");
        }

        return WanPisuConstants.wanPisus;

    }

    public static ArrayList<String> getAnimeServer(String animeID, String episodeNumber) {
        String serverURL = "https://embed.ssbcontent.site/apivtwo/clock.json?id=" + decryptAllAnime(animeID, episodeNumber);
        Log.e("TAG", serverURL);

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

            if (linkObject.has("mp4")) {
                WanPisuConstants.isHLS = !linkObject.getBoolean("mp4");
            } else {
                WanPisuConstants.isHLS = true;
            }

            Log.e("TAG", "HLS KEY: " + linkObject);

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

        try(Response response = client.newCall(request).execute()) {
            String rawJSON;
            if (response.body() != null) {
                rawJSON = response.body().string();
                Log.e("TAG", rawJSON);
                JSONObject jsonObject = new JSONObject(rawJSON);
                JSONArray sourceURLs = jsonObject.getJSONObject("data").getJSONObject("episode").getJSONArray("sourceUrls");

                for (int i = 0; i < sourceURLs.length(); i++) {
                    String decrypted = decryptAllAnimeServer(sourceURLs.getJSONObject(i).getString("sourceUrl").substring(2));
                    if (decrypted.contains("apivtwo")) {
                        decrypted = decrypted.substring(18);
                        Log.e("TAG", "Decrypted: " + decrypted);
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

    // TODO Store Anilist Data locally
    public static void getUsersAnime() {
        WanPisuConstants.wanPisus = new ArrayList<>();
        String TOKEN = WanPisuConstants.preferences.getString(WanPisuConstants.WAN_PISU_ANILIST_TOKEN, "");
        String userName = WanPisuConstants.preferences.getString(WanPisuConstants.ANILIST_USER_NAME, "");
        ArrayList<Anilist> anilists = AnilistParser
                .getAnimeDetails(
                        userName,
                        WanPisuConstants.ANIME_CURRENT,
                        TOKEN
                );
        for (int i = 0; i < anilists.size(); i++) {
            Anilist anilist = anilists.get(i);
            WanPisuConstants.wanPisus.add(new WanPisu(
                    "",
                    anilist.getAnimeName(),
                    anilist.getAnimeImageUrl(),
                    anilists.get(i).getMalID(),
                    null
            ));
        }

    }
}
