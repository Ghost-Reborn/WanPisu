package in.ghostreborn.wanpisu.parser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestParser {

    public static String getAllAnimeID(String anime, String allAnimeID) {

        OkHttpClient client = new OkHttpClient();

        String baseUrl = "https://api.allanime.day/api";
        String queryUrl = baseUrl + "?variables=" + Uri.encode("{\"showId\":\"" + "ReooPAxPMsHM4KPMY" + "\",\"translationType\":\"sub\",\"episodeString\":\"" +
                "1100" +
                "\"}") + "&query=" + Uri.encode("query($showId:String!,$translationType:VaildTranslationTypeEnumType!,$episodeString:String!){episode(showId:$showId,translationType:$translationType,episodeString:$episodeString){}}");

        /**
         * This is the Test Query
         */
        queryUrl = "https://api.allanime.day/api?variables={%22showId%22:%22ReooPAxPMsHM4KPMY%22,%22translationType%22:%22sub%22,%22episodeString%22:%221100%22}&extensions={%22persistedQuery%22:{%22version%22:1,%22sha256Hash%22:%225f1a64b73793cc2234a389cf3a8f93ad82de7043017dd551f38f65b89daa65e0%22}}";

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
                if (animeID.equals(allAnimeID)){
                    return edges.getJSONObject("availableEpisodesDetail")
                            .getString("sub");
                }
            }
        } catch (JSONException e) {
            Log.e("TAG", e.getCause() + "");
        }

        return rawJson;

    }

}
