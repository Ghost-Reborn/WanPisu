package in.ghostreborn.wanpisu.parser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.AllManga;
import in.ghostreborn.wanpisu.model.Manga;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MangaParser {

    private static String connectAndGetJSON(String url) {
        OkHttpClient client = new OkHttpClient();

        String rawJSON = null;

        Request request = new Request.Builder().url(url).header("Referer", "https://allanime.to").header("Cipher", "AES256-SHA256").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0").build();
        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                rawJSON = response.body().string();
            }
        } catch (IOException e) {
            Log.e("TAG", e.getCause() + "");
        }

        return rawJSON;

    }

    public static String testManga(String mangaName) {
        /*
          Source Names
          YoutubeAnime - https://ytimgf.youtube-anime.com/
         */

        OkHttpClient client = new OkHttpClient();

        // For getting manga
        String baseUrl = "https://api.allanime.day/api";
        String queryUrl = baseUrl + "?variables=" + Uri.encode("{\"search\":{\"query\":\""
                + mangaName
                + "\",\"isManga\":true},\"limit\":39,\"page\":1,\"translationType\":\"sub\",\"countryOrigin\":\"ALL\"}") + "&query=" + Uri.encode("query($search:SearchInput,$limit:Int,$page:Int,$translationType:VaildTranslationTypeMangaEnumType,$countryOrigin:VaildCountryOriginEnumType){mangas(search:$search,limit:$limit,page:$page,translationType:$translationType,countryOrigin:$countryOrigin){edges{" +
                "name," +
                "_id," +
                "availableChaptersDetail" +
                "}}}");

        Request request = new Request.Builder().url(queryUrl).header("Referer", "https://allanime.to").header("Cipher", "AES256-SHA256").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0").build();
        String rawJson = "NULL";

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                rawJson = response.body().string();
            }
            Log.e("TAG", rawJson);
        } catch (IOException e) {
            Log.e("TAG", e.getCause() + "");
        }

        return rawJson;

    }

    public static void searchManga(String mangaName) {
        // For searching
        String baseUrl = "https://api.allanime.day/api";
        String queryUrl = baseUrl + "?variables=" + Uri.encode("{\"search\":{\"query\":\""
                + mangaName
                + "\",\"isManga\":true},\"limit\":39,\"page\":1,\"translationType\":\"sub\",\"countryOrigin\":\"ALL\"}") + "&query=" + Uri.encode("query($search:SearchInput,$limit:Int,$page:Int,$translationType:VaildTranslationTypeMangaEnumType,$countryOrigin:VaildCountryOriginEnumType){mangas(search:$search,limit:$limit,page:$page,translationType:$translationType,countryOrigin:$countryOrigin){edges{" +
                "name," +
                "_id," +
                "thumbnail," +
                "availableChaptersDetail" +
                "}}}");

        String rawJSON = connectAndGetJSON(queryUrl);
        WanPisuConstants.allMangas = new ArrayList<>();
        try {
            JSONObject rawObject = new JSONObject(rawJSON);
            JSONArray edgesArray = rawObject
                    .getJSONObject("data")
                    .getJSONObject("mangas")
                    .getJSONArray("edges");
            ArrayList<String> chapters;

            for (int i = 0; i < edgesArray.length(); i++) {
                JSONObject edges = edgesArray.getJSONObject(i);
                String name = edges.getString("name");
                String id = edges.getString("_id");
                String thumbnail = "https://wp.youtube-anime.com/aln.youtube-anime.com/" +
                        edges.getString("thumbnail");
                chapters = new ArrayList<>();
                JSONArray subArray = edges
                        .getJSONObject("availableChaptersDetail")
                                .getJSONArray("sub");
                for (int j=subArray.length() - 1; j>=0; j--){
                    chapters.add(subArray.getString(j));
                }
                WanPisuConstants.allMangas.add(new AllManga(name, id, thumbnail, chapters));
            }

        } catch (JSONException e) {
            Log.e("TAG", e.getCause() + "");
        }

    }

    public static ArrayList<Manga> getManga(String mangaID, String chapter) {
        // For getting manga
        String baseUrl = "https://api.allanime.day/api";
        String queryUrl = baseUrl + "?variables=" + Uri.encode("{\"mangaId\":\"" +
                mangaID +
                "\",\"limit\":39,\"page\":1,\"translationType\":\"sub\",\"chapterString\":\"" +
                chapter +
                "\"}") + "&query=" + Uri.encode("query($chapterString:String!,$mangaId:String!,$limit:Int,$page:Int,$translationType:VaildTranslationTypeMangaEnumType!){chapterPages(chapterString:$chapterString,mangaId:$mangaId,limit:$limit,page:$page,translationType:$translationType){edges{" +
                "sourceName," +
                "pictureUrls" +
                "}}}");

        String rawJSON = connectAndGetJSON(queryUrl);
        ArrayList<Manga> mangas = new ArrayList<>();
        try {
            JSONObject rawObject = new JSONObject(rawJSON);
            JSONObject edgesObject = rawObject
                    .getJSONObject("data")
                    .getJSONObject("chapterPages")
                    .getJSONArray("edges")
                    .getJSONObject(0);
            JSONArray pictureUrls = edgesObject
                    .getJSONArray("pictureUrls");
            for (int i = 0; i < pictureUrls.length(); i++) {
                JSONObject pictureUrlObject = pictureUrls.getJSONObject(i);
                String page = pictureUrlObject.getString("num");
                String url = "https://ytimgf.youtube-anime.com/" +
                        pictureUrlObject.getString("url");
                mangas.add(new Manga(page, url));
            }
        } catch (JSONException e) {
            Log.e("TAG", e.getCause() + "");
        }

        return mangas;
    }

}
