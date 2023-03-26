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

import in.ghostreborn.wanpisu.MainActivity;
import in.ghostreborn.wanpisu.model.WanPisu;

public class AllAnime {

    public static final String ALL_ANIME_QUERY_HEAD = "https://api.allanime.to/allanimeapi?variables={\"search\":{\"allowAdult\":false,\"allowUnknown\":false,\"query\":\"";
    public static final String ALL_ANIME_QUERY_TAIL = "\"},\"limit\":40,\"page\":1,\"translationType\":\"sub\",\"countryOrigin\":\"ALL\"}&query=query($search:SearchInput,$limit:Int,$page:Int,$translationType:VaildTranslationTypeEnumType,$countryOrigin:VaildCountryOriginEnumType){shows(search:$search,limit:$limit,page:$page,translationType:$translationType,countryOrigin:$countryOrigin){edges{_id,name,thumbnail,availableEpisodes}}}";
    public static final String ALL_ANIME_SERVER_HEAD = "https://api.allanime.to/allanimeapi?variables={%22showId%22:%22";
    public static final String ALL_ANIME_SERVER_MIDDLE = "%22,%22translationType%22:%22sub%22,%22episodeString%22:%22";
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
            Log.e(MainActivity.LOG_TAG, "Unable to parse URL");
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
                animeDetailsArray.add(new WanPisu(
                        animeID,
                        animeName,
                        animeThumbnailUrl,
                        Integer.parseInt(lastEpisode)
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return animeDetailsArray;

    }

    public static String getAnimeServer(String animeID, String episodeNumber) {
        String apiUrl = ALL_ANIME_SERVER_HEAD + animeID + ALL_ANIME_SERVER_MIDDLE + episodeNumber + ALL_ANIME_SERVER_TAIL;
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
                    break;
                }
            }
            apiUrl = ALL_ANIME_BLOG_HEAD + apiClock.substring(15);
            Log.e("ALLANIME", apiUrl);
            baseJSON = new JSONObject(connectAndGetJsonSearchData(apiUrl));
            JSONArray links = baseJSON.getJSONArray("links");
            String streamURL = "";
            for (int i=0;i<links.length();i++){
                JSONObject linkObject = links.getJSONObject(i);
                String link = linkObject.getString("link");
                if (linkObject.has("mp4")){
                    isHLS = !linkObject.getBoolean("mp4");
                }
                if (!link.contains("vipanicdn")){
                    streamURL = link;
                    break;
                }
            }
            return streamURL;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";

    }

}
