package in.ghostreborn.wanpisu.parser.ServerParser;

import static in.ghostreborn.wanpisu.constants.WanPisuConstants.AGENT;
import static in.ghostreborn.wanpisu.constants.WanPisuConstants.ALL_ANIME_REFER;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SakParser {

    public static String parseSak(String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).header("Referer", ALL_ANIME_REFER).header("Cipher", "AES256-SHA256").header("User-Agent", AGENT).build();
        String rawJson = "NULL";
        StringBuilder out = new StringBuilder();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                rawJson = response.body().string();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            JSONArray links = new JSONObject(rawJson)
                    .getJSONArray("links");
            for (int i = 0; i < links.length(); i++) {
                String link = links.getJSONObject(i)
                        .getString("link");
                out.append(link).append("\n\n");
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return out.toString();

    }

}
