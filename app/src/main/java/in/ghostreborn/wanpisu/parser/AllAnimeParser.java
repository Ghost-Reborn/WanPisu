package in.ghostreborn.wanpisu.parser;

import static in.ghostreborn.wanpisu.constants.WanPisuConstants.AGENT;
import static in.ghostreborn.wanpisu.constants.WanPisuConstants.ALL_ANIME_API;
import static in.ghostreborn.wanpisu.constants.WanPisuConstants.ALL_ANIME_BASE;
import static in.ghostreborn.wanpisu.constants.WanPisuConstants.ALL_ANIME_REFER;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.Servers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Updated Parser for AllAnime
 */
public class AllAnimeParser {

    // TODO scrape id and name, available episodes will be taken when tapped on anime
    public static String parseAnimeByName(String anime) {
        OkHttpClient client = new OkHttpClient();
        String queryUrl = ALL_ANIME_API + "?variables=" + Uri.encode("{\"search\":{\"allowAdult\":false,\"allowUnknown\":false,\"query\":\"" + anime + "\"},\"limit\":39,\"page\":1,\"translationType\":\"sub\",\"countryOrigin\":\"ALL\"}") + "&query=" + Uri.encode("query($search:SearchInput,$limit:Int,$page:Int,$translationType:VaildTranslationTypeEnumType,$countryOrigin:VaildCountryOriginEnumType){shows(search:$search,limit:$limit,page:$page,translationType:$translationType,countryOrigin:$countryOrigin){edges{" +
                "_id," +
                "name" +
                "}}}");
        Request request = new Request.Builder().url(queryUrl).header("Referer", ALL_ANIME_REFER).header("Cipher", "AES256-SHA256").header("User-Agent", AGENT).build();
        String rawJson = "NULL";

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                rawJson = response.body().string();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return rawJson;

    }

    // TODO scrape available episodes and show it in episode selection
    public static String parseAnimeByID(String animeID) {
        OkHttpClient client = new OkHttpClient();
        String queryUrl = ALL_ANIME_API + "?variables=" + Uri.encode("{\"showId\":\"LYKSutL2PaAjYyXWz\"}") + "&query=" + Uri.encode("query ($showId: String!) {    show(        _id: $showId    ) {        " +
                "_id," +
                "availableEpisodesDetail" +
                "}}");
        Request request = new Request.Builder().url(queryUrl).header("Referer", ALL_ANIME_REFER).header("Cipher", "AES256-SHA256").header("User-Agent", AGENT).build();
        String rawJson = "NULL";

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                rawJson = response.body().string();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return rawJson;

    }

    // TODO Show user selection of servers from list
    //  Sak
    //  Luf-mp4
    //  Default
    //  S-mp4
    //  Uv-mp4
    public static void getEpisodeServers(String showID, String episode) {
        OkHttpClient client = new OkHttpClient();

        WanPisuConstants.servers = new ArrayList<>();

        String queryUrl = ALL_ANIME_API + "?variables=" + Uri.encode("{\"showId\":\"" +
                showID +
                "\",\"translationType\":\"sub\",\"episodeString\":\"" +
                episode +
                "\"}") + "&query=" + Uri.encode("query ($showId: String!, $translationType: VaildTranslationTypeEnumType!, $episodeString: String!) {    episode(        showId: $showId        translationType: $translationType        episodeString: $episodeString    ) {        " +
                "episodeString," +
                "sourceUrls" +
                "    }}");
        Request request = new Request.Builder().url(queryUrl).header("Referer", ALL_ANIME_REFER).header("Cipher", "AES256-SHA256").header("User-Agent", AGENT).build();
        String rawJson = "NULL";

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                rawJson = response.body().string();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            JSONArray sourceUrls = new JSONObject(rawJson)
                    .getJSONObject("data")
                    .getJSONObject("episode")
                    .getJSONArray("sourceUrls");

            for (int i = 0; i < sourceUrls.length(); i++) {
                JSONObject sourceObject = sourceUrls.getJSONObject(i);
                String sourceUrl = sourceObject.getString("sourceUrl");
                if (sourceUrl.contains("--")) {
                    sourceUrl = "https://" + ALL_ANIME_BASE + AllAnimeUtils.decryptAllAnimeServer(sourceUrl.substring(2))
                            .replace("clock", "clock.json");
                    if (sourceUrl.contains("clock")) {
                        String sourceName = sourceObject.getString("sourceName");

                        if (sourceName.equals(WanPisuConstants.SERVER_SAK)){
                            WanPisuConstants.SAK_URL = sourceUrl;
                        }else if (sourceName.equals(WanPisuConstants.SERVER_DEFAULT)) {
                            WanPisuConstants.DEFAULT_URL = sourceUrl;
                        }else if (sourceName.equals(WanPisuConstants.SERVER_LUF_MP4)){
                            WanPisuConstants.LUF_MP4_URL = sourceUrl;
                        }else if (sourceName.equals(WanPisuConstants.SERVER_UV_MP4)){
                            WanPisuConstants.UV_MP4_URL = sourceUrl;
                        }else if (sourceName.equals(WanPisuConstants.SERVER_S_MP4)){
                            WanPisuConstants.S_MP4_URL = sourceUrl;
                        }

                        WanPisuConstants.servers.add(new Servers(sourceName, sourceUrl));
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
