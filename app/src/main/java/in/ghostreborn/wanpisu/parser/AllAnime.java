package in.ghostreborn.wanpisu.parser;

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
import in.ghostreborn.wanpisu.model.WanPisu;
import in.ghostreborn.wanpisu.ui.WanPisuActivity;

public class AllAnime {

    static boolean isDubEnabled = WanPisuConstants.preferences.getBoolean(WanPisuConstants.WAN_PISU_PREFERENCE_ENABLE_DUB, false);
    static boolean isUnknownEnabled = WanPisuConstants.preferences.getBoolean(WanPisuConstants.WAN_PISU_PREFERENCE_ENABLE_UNKNOWN, false);
    public static final String ALL_ANIME_QUERY_HEAD = "https://api.allanime.to/allanimeapi?variables={\"search\":{\"allowAdult\":" +
            true +
            ",\"allowUnknown\":" +
            isUnknownEnabled +
            ",\"query\":\"";
    public static String ALL_ANIME_QUERY_TAIL = "\"},\"limit\":40,\"page\":1,\"translationType\":\"" +
            (isDubEnabled ? "dub" : "sub") +
            "\",\"countryOrigin\":\"ALL\"}&query=query($search:SearchInput,$limit:Int,$page:Int,$translationType:VaildTranslationTypeEnumType,$countryOrigin:VaildCountryOriginEnumType){shows(search:$search,limit:$limit,page:$page,translationType:$translationType,countryOrigin:$countryOrigin){edges{_id,name,thumbnail,availableEpisodes}}}";
    public static final String ALL_ANIME_SERVER_HEAD = "https://api.allanime.to/allanimeapi?variables={%22showId%22:%22";
    public static final String ALL_ANIME_SERVER_MIDDLE = "%22,%22translationType%22:%22" +
            (isDubEnabled ? "dub" : "sub") +
            "%22,%22episodeString%22:%22";
    public static final String ALL_ANIME_SERVER_TAIL = "%22}&query=query($showId:String!,$translationType:VaildTranslationTypeEnumType!,$episodeString:String!){episode(showId:$showId,translationType:$translationType,episodeString:$episodeString){episodeString,sourceUrls}}";
    public static final String ALL_ANIME_BLOG_HEAD = "https://blog.allanime.pro/apivtwo/clock.json?";

    public static boolean isHLS = false;

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
            Log.e(WanPisuActivity.LOG_TAG, "Unable to parse URL");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    /**
     * JSON array with `edges` provides anime details
     *
     * @return ArrayList<WanPisu>
     */
    public static ArrayList<WanPisu> parseAnimeIDAnimeNameAnimeThumbnail(String anime) {

        String rawJson = connectAndGetJsonSearchData(
                ALL_ANIME_QUERY_HEAD + anime + ALL_ANIME_QUERY_TAIL
        );

        ArrayList<WanPisu> animeDetailsArray = new ArrayList<>();
        try {
            JSONArray edgesArray = new JSONObject(rawJson)
                    .getJSONObject("data")
                    .getJSONObject("shows")
                    .getJSONArray("edges");
            for (int i = 0; i < edgesArray.length(); i++) {
                JSONObject edges = edgesArray.getJSONObject(i);
                String animeID = edges.getString("_id");
                String animeName = edges.getString("name");
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
                animeDetailsArray.add(new WanPisu(
                        animeID,
                        animeName,
                        url + animeThumbnailUrl,
                        Integer.parseInt(lastEpisode)
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return animeDetailsArray;

    }

    public static ArrayList<String> getAnimeServer(String animeID, String episodeNumber) {
        String apiUrl = ALL_ANIME_SERVER_HEAD + animeID + ALL_ANIME_SERVER_MIDDLE + episodeNumber + ALL_ANIME_SERVER_TAIL;
        ArrayList<String> servers = new ArrayList<>();
        String apiClock = "";
        try {
            JSONObject baseJSON = new JSONObject(connectAndGetJsonSearchData(apiUrl));
            JSONArray sourceURLs = baseJSON.
                    getJSONObject("data")
                    .getJSONObject("episode")
                    .getJSONArray("sourceUrls");
            for (int i = 0; i < sourceURLs.length(); i++) {
                String server = sourceURLs.getJSONObject(i).getString("sourceUrl");
                if (server.contains("apivtwo")) {
                    apiClock = server;
                    continue;
                }
                servers.add(server);
            }
            apiUrl = ALL_ANIME_BLOG_HEAD + apiClock.substring(15);
            Log.e("ALLANIME", apiUrl);
            baseJSON = new JSONObject(connectAndGetJsonSearchData(apiUrl));
            JSONArray links = baseJSON.getJSONArray("links");
            for (int i = 0; i < links.length(); i++) {
                JSONObject linkObject = links.getJSONObject(i);
                String server = linkObject.getString("link");
                if (linkObject.has("mp4")) {
                    isHLS = !linkObject.getBoolean("mp4");
                }
                servers.add(server);
            }
            return servers;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return servers;

    }

}
