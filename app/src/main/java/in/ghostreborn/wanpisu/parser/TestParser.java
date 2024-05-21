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

    public static String testParse() {

        OkHttpClient client = new OkHttpClient();
        String baseUrl = "https://api.allanime.day/api";
        String queryUrl = baseUrl + "?variables=" + Uri.encode("{\"showId\":\"LYKSutL2PaAjYyXWz\",\"translationType\":\"sub\",\"episodeString\":\"1\"}") + "&query=" + Uri.encode("query ($showId: String!, $translationType: VaildTranslationTypeEnumType!, $episodeString: String!) {    episode(        showId: $showId        translationType: $translationType        episodeString: $episodeString    ) {        episodeString sourceUrls    }}");

        queryUrl = "https://api.allanime.day/api?variables={%22_id%22:%22ReooPAxPMsHM4KPMY%22}&extensions={%22persistedQuery%22:{%22version%22:1,%22sha256Hash%22:%229d7439c90f203e534ca778c4901f9aa2d3ad42c06243ab2c5e6b79612af32028%22}}";

        Request request = new Request.Builder().url(queryUrl).header("Referer", "https://allanime.to").header("Cipher", "AES256-SHA256").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0").build();
        String rawJson = "NULL";

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                rawJson = response.body().string();
            }
        } catch (IOException e) {
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
