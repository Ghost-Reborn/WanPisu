package in.ghostreborn.wanpisu.parser;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.model.Anilist;
import in.ghostreborn.wanpisu.model.WanPisu;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AllAnime {

    public static final String ALL_ANIME_SERVER_HEAD = "https://api.allanime.to/allanimeapi?variables={%22showId%22:%22";
    public static final String ALL_ANIME_SERVER_TAIL = "%22}&query=query($showId:String!,$translationType:VaildTranslationTypeEnumType!,$episodeString:String!){episode(showId:$showId,translationType:$translationType,episodeString:$episodeString){episodeString,sourceUrls}}";
    public static final String ALL_ANIME_BLOG_HEAD = "https://blog.allanime.pro/apivtwo/clock.json?";
    // TODO fix this
//    static boolean isDubEnabled = WanPisuConstants.preferences.getBoolean(WanPisuConstants.WAN_PISU_PREFERENCE_ENABLE_DUB, false);
    static boolean isDubEnabled = false;
    public static String ALL_ANIME_QUERY_TAIL = "\"},\"limit\":40,\"page\":1,\"translationType\":\"" +
            (isDubEnabled ? "dub" : "sub") +
            "\",\"countryOrigin\":\"ALL\"}&query=query($search:SearchInput,$limit:Int,$page:Int,$translationType:VaildTranslationTypeEnumType,$countryOrigin:VaildCountryOriginEnumType){shows(search:$search,limit:$limit,page:$page,translationType:$translationType,countryOrigin:$countryOrigin){edges{_id,name,thumbnail,availableEpisodes,malId,englishName}}}";
    public static final String ALL_ANIME_SERVER_MIDDLE = "%22,%22translationType%22:%22" +
            (isDubEnabled ? "dub" : "sub") +
            "%22,%22episodeString%22:%22";
    // TODO fix this
//    static boolean isUnknownEnabled = WanPisuConstants.preferences.getBoolean(WanPisuConstants.WAN_PISU_PREFERENCE_ENABLE_UNKNOWN, false);
    static boolean isUnknownEnabled = false;
    public static final String ALL_ANIME_QUERY_HEAD = "https://api.allanime.to/allanimeapi?variables={\"search\":{\"allowAdult\":" +
            true +
            ",\"allowUnknown\":" +
            isUnknownEnabled +
            ",\"query\":\"";

    private static String connectAndGetJsonSearchData(String url) {

        StringBuilder result = new StringBuilder();
        try {
            URL queryURL = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) queryURL.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            Log.e("TAG", "Unable to parse URL");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    private static String getAllAnimeJSON(String animeName) {
        OkHttpClient client = new OkHttpClient();

        String baseUrl = "https://api.allanime.day/api";
        String queryUrl = baseUrl + "?variables=" + Uri.encode("{\"search\":{\"allowAdult\":false,\"allowUnknown\":false,\"query\":\"" + animeName + "\"},\"limit\":40,\"page\":1,\"translationType\":\"sub\",\"countryOrigin\":\"ALL\"}") + "&query=" + Uri.encode("query($search:SearchInput,$limit:Int,$page:Int,$translationType:VaildTranslationTypeEnumType,$countryOrigin:VaildCountryOriginEnumType){shows(search:$search,limit:$limit,page:$page,translationType:$translationType,countryOrigin:$countryOrigin){edges{_id,name,englishName,availableEpisodes,__typename,malId,thumbnail,lastEpisodeInfo,lastEpisodeDate,season,airedStart,episodeDuration,episodeCount,lastUpdateEnd}}}");

        Request request = new Request.Builder().url(queryUrl).header("Referer", "https://allanime.to").header("Cipher", "AES256-SHA256").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0").build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "NULL";

    }

    /**
     * JSON array with `edges` provides anime details
     *
     * @return ArrayList<WanPisu>
     */
    public static ArrayList<WanPisu> parseAnimeIDAnimeNameAnimeThumbnail(String anime) {

        String rawJson = getAllAnimeJSON(anime);

        WanPisuConstants.wanPisus = new ArrayList<>();
        try {
            JSONArray edgesArray = new JSONObject(rawJson)
                    .getJSONObject("data")
                    .getJSONObject("shows")
                    .getJSONArray("edges");
            for (int i = 0; i < edgesArray.length(); i++) {
                JSONObject edges = edgesArray.getJSONObject(i);
                String malID = edges.getString("malId");
                if (malID.equals("null")) continue;
                String animeID = edges.getString("_id");
                String animeName = edges.getString("name");
                String animeEnglishName = edges.getString("englishName");
                if (!animeEnglishName.equals("null")) {
                    animeName = animeEnglishName;
                }
                String animeThumbnailUrl = edges.getString("thumbnail");
                String lastEpisode = edges.getJSONObject("availableEpisodes")
                        .getString("sub");
                String url = "";
                if (animeThumbnailUrl.contains("__Show__")) {
                    if (animeThumbnailUrl.contains("images"))
                        url = "https://wp.youtube-anime.com/aln.youtube-anime.com/";
                    else {
                        url = "https://wp.youtube-anime.com/aln.youtube-anime.com/images/";
                    }
                }
                WanPisuConstants.wanPisus.add(new WanPisu(
                        animeID,
                        animeName,
                        url + animeThumbnailUrl,
                        Integer.parseInt(lastEpisode),
                        malID
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return WanPisuConstants.wanPisus;

    }

    public static ArrayList<String> getAnimeServer(String animeID, String episodeNumber) {
        String serverURL = "https://embed.ssbcontent.site/apivtwo/clock.json?id=" + decryptAllAnime(animeID, episodeNumber);
        Log.e("TAG", serverURL);

        ArrayList<String> servers = new ArrayList<>();
        String apiClock = "";
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
                // TODO check HLS key exists in response
                WanPisuConstants.isHLS = true;
            }

            return servers;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
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

        try {
            Response response = client.newCall(request).execute();
            String rawJSON = response.body().string();
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

            return "NULL";

        } catch (IOException | JSONException e) {
            e.printStackTrace();
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

    public static ArrayList<WanPisu> getUsersAnime(Context context) {
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
                    0,
                    anilists.get(i).getMalID()
            ));
        }

        return WanPisuConstants.wanPisus;
    }
}
