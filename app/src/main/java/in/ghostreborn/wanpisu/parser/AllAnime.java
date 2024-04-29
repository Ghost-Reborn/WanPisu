package in.ghostreborn.wanpisu.parser;

import android.net.Uri;

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
import in.ghostreborn.wanpisu.model.WanPisuEpisodes;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AllAnime {

    /**
     * anime - name of anime
     * If not provided, returns recently updated anime
     */
    public static void getAnimes(String anime) {

        WanPisuConstants.wanPisus = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        String baseUrl = "https://api.allanime.day/api";
        String queryUrl = baseUrl + "?variables=" + Uri.encode("{\"search\":{\"allowAdult\":false,\"allowUnknown\":false,\"query\":\"" + anime + "\"},\"limit\":39,\"page\":1,\"translationType\":\"sub\",\"countryOrigin\":\"ALL\"}") + "&query=" + Uri.encode("query($search:SearchInput,$limit:Int,$page:Int,$translationType:VaildTranslationTypeEnumType,$countryOrigin:VaildCountryOriginEnumType){shows(search:$search,limit:$limit,page:$page,translationType:$translationType,countryOrigin:$countryOrigin){edges{" + "_id," + "name," + "thumbnail," + "lastEpisodeInfo," + "availableEpisodesDetail" + "}}}");
        Request request = new Request.Builder().url(queryUrl).header("Referer", "https://allanime.to").header("Cipher", "AES256-SHA256").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0").build();
        String rawJson = "NULL";

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                rawJson = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONArray edgesArray = new JSONObject(rawJson).getJSONObject("data").getJSONObject("shows").getJSONArray("edges");
            for (int i = 0; i < edgesArray.length(); i++) {
                JSONObject edge = edgesArray.getJSONObject(i);
                String id = edge.getString("_id");
                String name = edge.getString("name");
                String thumbnail = edge.getString("thumbnail");
                String lastEpisode = edge.getJSONObject("lastEpisodeInfo").getJSONObject("sub").getString("episodeString");
                ArrayList<WanPisuEpisodes> availableEpisodes = new ArrayList<>();
                JSONArray episodesArray = edge.getJSONObject("availableEpisodesDetail").getJSONArray("sub");
                for (int j = episodesArray.length() - 1; j >= 0; j--) {
                    availableEpisodes.add(new WanPisuEpisodes(episodesArray.getString(j), "", ""));
                }
                WanPisuConstants.wanPisus.add(new WanPisu(id, name, thumbnail, lastEpisode, availableEpisodes));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<String> getEpisodeName(String animeID, String episode) {

        WanPisuConstants.wanPisus = new ArrayList<>();
        ArrayList<String> titleAndThumbnail = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        String baseUrl = "https://api.allanime.day/api";
        String queryUrl = baseUrl + "?variables=" + Uri.encode("{\"showId\":\"" + animeID + "\",\"translationType\":\"sub\",\"episodeString\":\"" + episode + "\"}") + "&query=" + Uri.encode("query($showId:String!,$translationType:VaildTranslationTypeEnumType!,$episodeString:String!){episode(showId:$showId,translationType:$translationType,episodeString:$episodeString){" + "episodeInfo{" + "notes," + "thumbnails" + "}" + "}}");

        Request request = new Request.Builder().url(queryUrl).header("Referer", "https://allanime.to").header("Cipher", "AES256-SHA256").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0").build();
        String rawJson = "{}";

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                rawJson = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject episodeObj = new JSONObject(rawJson).getJSONObject("data").getJSONObject("episode");
            JSONObject episodeInfo;
            if (!episodeObj.isNull("episodeInfo")) {
                episodeInfo = episodeObj.getJSONObject("episodeInfo");
                String episodeName = "";
                if (!episodeInfo.isNull("notes")) {
                    episodeName = episodeInfo.getString("notes");
                }
                titleAndThumbnail.add(episodeName);
                if (!episodeInfo.isNull("thumbnails")) {
                    String thumbnail = "https://wp.youtube-anime.com/aln.youtube-anime.com" + episodeInfo.getJSONArray("thumbnails").getString(0);
                    titleAndThumbnail.add(thumbnail);
                    return titleAndThumbnail;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (titleAndThumbnail.isEmpty()) {
            titleAndThumbnail.add("");
            titleAndThumbnail.add("");
        } else if (titleAndThumbnail.size() == 1) {
            titleAndThumbnail.add("");
        }
        return titleAndThumbnail;

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
            JSONArray sourceURLs = baseJSON.getJSONArray("links");
            JSONObject linkObject = sourceURLs.getJSONObject(0);
            String server = linkObject.getString("link");
            servers.add(server);

            if (linkObject.has("mp4")) {
                WanPisuConstants.isHLS = !linkObject.getBoolean("mp4");
            } else {
                WanPisuConstants.isHLS = true;
            }

            return servers;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return servers;

    }

    public static String decryptAllAnime(String showID, String episodeNumber) {

        // Connect and get encrypted url

        OkHttpClient client = new OkHttpClient();

        String baseUrl = "https://api.allanime.day/api";
        String queryUrl = baseUrl + "?variables=" + Uri.encode("{\"showId\":\"" + showID + "\",\"translationType\":\"sub\",\"episodeString\":\"" + episodeNumber + "\"}") + "&query=" + Uri.encode("query($showId:String!,$translationType:VaildTranslationTypeEnumType!,$episodeString:String!){episode(showId:$showId,translationType:$translationType,episodeString:$episodeString){episodeString,sourceUrls}}");

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
            e.printStackTrace();
        }

        return "NULL";

    }

    public static String decryptAllAnimeServer(String decrypt) {
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

}
