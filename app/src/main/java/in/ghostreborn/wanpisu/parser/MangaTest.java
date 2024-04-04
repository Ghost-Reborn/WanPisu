package in.ghostreborn.wanpisu.parser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MangaTest {
    public static String testManga(String mangaName){

        /**
         * Source Names
         * YoutubeAnime - https://ytimgf.youtube-anime.com/
         */

        OkHttpClient client = new OkHttpClient();

        // For searching
        String baseUrl = "https://api.allanime.day/api";
        String queryUrl = baseUrl + "?variables=" + Uri.encode("{\"search\":{\"query\":\""
                + mangaName
                + "\",\"isManga\":true},\"limit\":39,\"page\":1,\"translationType\":\"sub\",\"countryOrigin\":\"ALL\"}") + "&query=" + Uri.encode("query($search:SearchInput,$limit:Int,$page:Int,$translationType:VaildTranslationTypeMangaEnumType,$countryOrigin:VaildCountryOriginEnumType){mangas(search:$search,limit:$limit,page:$page,translationType:$translationType,countryOrigin:$countryOrigin){edges{" +
                "name" +
                "}}}");

        // For getting manga
        queryUrl = baseUrl + "?variables=" + Uri.encode("{\"mangaId\":\"ex9vXC6gWYY9bGkSo\",\"limit\":39,\"page\":1,\"translationType\":\"sub\",\"chapterString\":\"0\"}") + "&query=" + Uri.encode("query($chapterString:String!,$mangaId:String!,$limit:Int,$page:Int,$translationType:VaildTranslationTypeMangaEnumType!){chapterPages(chapterString:$chapterString,mangaId:$mangaId,limit:$limit,page:$page,translationType:$translationType){edges{" +
                "sourceName," +
                "pictureUrls" +
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

        return rawJson;

    }
}
