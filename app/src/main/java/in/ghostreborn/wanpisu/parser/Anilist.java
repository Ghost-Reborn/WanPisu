package in.ghostreborn.wanpisu.parser;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Anilist {

    public static final String LOG_TAG = "WAN_PISU";

    // Token URLs
    public static final String CLIENT_ID = "11820";
    public static final String ANILIST_BASE_URL = "https://anilist.co/";
    public static final String ANILIST_TOKEN_URL = ANILIST_BASE_URL + "api/v2/oauth/authorize?client_id=" + CLIENT_ID + "&response_type=token";

    // Query API URLs
    public static final String QUERY_API_BASE = "https://graphql.anilist.co/";

    public static String getAnilistUserDetails(String ACCESS_TOKEN) {
        String QUERY = "query { " +
                "Viewer {" +
                "id " +
                "name " +
                "about " +
                "avatar {" +
                "large " +
                "medium " +
                "} " +
                "bannerImage " +
                "isFollowing " +
                "isFollower " +
                "isBlocked " +
                "bans " +
                "options {" +
                "titleLanguage " +
                "displayAdultContent " +
                "airingNotifications " +
                "profileColor " +
                "timezone " +
                "activityMergeTime " +
                "staffNameLanguage " +
                "restrictMessagesToFollowing " +
                "} " +
                "mediaListOptions {" +
                "scoreFormat " +
                "rowOrder " +
                "useLegacyLists " +
                "sharedTheme " +
                "sharedThemeEnabled " +
                "} " +
                "unreadNotificationCount " +
                "siteUrl " +
                "donatorTier " +
                "donatorBadge " +
                "createdAt " +
                "updatedAt " +
                "previousNames {" +
                "name " +
                "createdAt " +
                "updatedAt " +
                "} " +
                "} " +
                "}";

        try {
            URL url = new URL(QUERY_API_BASE);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + ACCESS_TOKEN);

            JSONObject requestBody = new JSONObject();
            requestBody.put("query", QUERY);
            requestBody.put("variables", new JSONObject());

            conn.setDoOutput(true);
            conn.getOutputStream().write(requestBody.toString().getBytes());

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            conn.disconnect();

            return getImageURL(response.toString());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return "CHECK INTERNET CONNECTION AND TRY AGAIN";

    }

    private static String getImageURL(String baseJSON){
        try {
            String imageURL = new JSONObject(baseJSON)
                    .getJSONObject("data")
                    .getJSONObject("Viewer")
                    .getJSONObject("avatar")
                    .getString("large");
            return imageURL;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "CHECK INTERNET CONNECTION AND TRY AGAIN";

    }

}
