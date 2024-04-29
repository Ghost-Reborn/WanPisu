package in.ghostreborn.wanpisu.parser;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestParser {

    // TODO check this for server selection
    // There are many source names, and parsing is different
    // Test Luf-mp4

    public static String getAllAnimeID(String anime, String allAnimeID) {

        OkHttpClient client = new OkHttpClient();
        String baseUrl = "https://api.allanime.day/api";
        String queryUrl = baseUrl + "?variables=" + Uri.encode("{\"showId\":\"LYKSutL2PaAjYyXWz\",\"translationType\":\"sub\",\"episodeString\":\"1\"}") + "&query=" + Uri.encode("query ($showId: String!, $translationType: VaildTranslationTypeEnumType!, $episodeString: String!) {    episode(        showId: $showId        translationType: $translationType        episodeString: $episodeString    ) {        episodeString sourceUrls    }}");

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
            JSONArray sourceUrls = new JSONObject(rawJson)
                    .getJSONObject("data")
                    .getJSONObject("episode")
                    .getJSONArray("sourceUrls");

            String sourceUrl = "";

            for (int i = 0; i < sourceUrls.length(); i++) {
                JSONObject source = sourceUrls.getJSONObject(i);
                sourceUrl = source.getString("sourceUrl");
                if (sourceUrl.contains("--")) {
                    sourceUrl = AllAnime.decryptAllAnimeServer(sourceUrl.substring(2));
                    if (sourceUrl.contains("clock")) {
                        String sourceName = source.getString("sourceName");
                        sourceUrl = "https://allanime.day" + sourceUrl.replace("clock", "clock.json");

                        if (sourceName.equals("Luf-mp4")) {
                        }
                    }
                }
            }
            return getClock(sourceUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rawJson;

    }

    public static String getClock(String url) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).header("Referer", "https://allanime.to").header("Cipher", "AES256-SHA256").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0").build();
        String rawJson = "NULL";

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                rawJson = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONArray links = new JSONObject(rawJson)
                    .getJSONArray("links");
            StringBuilder out = new StringBuilder();

            for (int i = 0; i < links.length(); i++) {
                JSONObject link = links.getJSONObject(i);
                JSONArray vidArray = link
                        .getJSONObject("rawUrls")
                        .getJSONArray("vids");
                for (int j = 0; j < vidArray.length(); j++) {
                    String vidUrl = vidArray.getJSONObject(i)
                            .getString("url");
                    out.append(vidUrl + "\n\n");
                }
            }

            return out.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rawJson;

    }

}
