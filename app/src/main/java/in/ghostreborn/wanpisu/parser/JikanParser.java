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
import in.ghostreborn.wanpisu.model.Jikan;
import in.ghostreborn.wanpisu.model.JikanEpisodes;

public class JikanParser {

    public static void parseAnimeFull(String malID){
        String url = "https://api.jikan.moe/v4/anime/" +
                malID +
                "/full";

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
            Log.e("TAG", e.getCause() + "");
        }

        try {
            JSONObject jikanObject = new JSONObject(result.toString());
            JSONObject dataObject = jikanObject.getJSONObject("data");
            String title = dataObject.getString("title");
            String thumbnail = dataObject
                    .getJSONObject("images")
                    .getJSONObject("jpg")
                    .getString("image_url");
            String synopsis = dataObject.getString("synopsis");

        } catch (JSONException e) {
            Log.e("TAG", e.getCause() + "");
        }
    }

    public static void parseAnimeEpisodes(String malID, String page){
        String url = "https://api.jikan.moe/v4/anime/" +
                malID +
                "/episodes?page=" +
                page;

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
            Log.e("TAG", e.getCause() + "");
        }

        try {
            JSONObject jikanObject = new JSONObject(result.toString());
            JSONArray episodesArray = jikanObject.getJSONArray("data");

            // Extract the episode names from the JSON array
            for (int i = 0; i < episodesArray.length(); i++) {
                JSONObject episode = episodesArray.getJSONObject(i);
                String episodeName = episode.getString("title");
                String episodeNumber = episode.getString("mal_id");
            }
        } catch (JSONException e) {
            Log.e("TAG", e.getCause() + "");
        }
    }

}
