package in.ghostreborn.wanpisu.parser;

import static in.ghostreborn.wanpisu.constants.WanPisuConstants.AGENT;
import static in.ghostreborn.wanpisu.constants.WanPisuConstants.ALL_ANIME_REFER;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LufMp4Parser {

    public static String parseLufMp4(String url) {
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

        return rawJson;

    }

}
