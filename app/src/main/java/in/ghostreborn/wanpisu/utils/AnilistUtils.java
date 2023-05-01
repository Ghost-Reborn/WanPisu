package in.ghostreborn.wanpisu.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.AnilistParser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AnilistUtils {

    public static void checkAnilist(Context context){
        SharedPreferences preferences = context.getSharedPreferences(WanPisuConstants.WAN_PISU_PREFERENCE, Context.MODE_PRIVATE);
        if (!preferences.contains(WanPisuConstants.WAN_PISU_ANILIST_TOKEN)){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(AnilistParser.ANILIST_TOKEN_URL));
            context.startActivity(intent);
            WanPisuConstants.isLogged = false;
        }else {
            String TOKEN = preferences.getString(WanPisuConstants.WAN_PISU_ANILIST_TOKEN, "");
            WanPisuConstants.isLogged = true;
            checkAnilistUser(context, TOKEN);
        }
    }

    private static void checkAnilistUser(Context context, String TOKEN){
        SharedPreferences preferences = context.getSharedPreferences(WanPisuConstants.WAN_PISU_PREFERENCE, Context.MODE_PRIVATE);
        if (!preferences.contains(WanPisuConstants.ANILIST_USER_NAME)){
            Executor executor = Executors.newSingleThreadExecutor();
            Runnable task = () -> {
                String userName = AnilistParser.getAnilistUserDetails(TOKEN);
                preferences.edit()
                        .putString(WanPisuConstants.ANILIST_USER_NAME, userName)
                        .apply();
            };
            executor.execute(task);
        }
    }

    public static void saveAnimeProgress(String id, String progress, String ACCESS_TOKEN){
        int animeId = Integer.parseInt(id); // The AniList ID of One Piece
        int newProgress = Integer.parseInt(progress); // The updated progress value

        int anilistID = Integer.parseInt(getAnimeID(animeId));
        try {
            String query = "mutation "
                    + "SaveMediaListEntry($mediaId: Int!, $progress: Int!) {\n"
                    + "  SaveMediaListEntry(mediaId: $mediaId, progress: $progress) {\n"
                    + "    id\n"
                    + "    progress\n"
                    + "  }\n"
                    + "}";
            Map<String, Object> variables = new HashMap<>();
            variables.put("mediaId", anilistID);
            variables.put("progress", newProgress);
            String requestBody = new Gson().toJson(new GraphQlRequestBody(query, variables));

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://graphql.anilist.co")
                    .header("Authorization", "Bearer " + ACCESS_TOKEN)
                    .post(RequestBody.create(MediaType.parse("application/json"), requestBody))
                    .build();

            Response response = client.newCall(request).execute();
            Log.e("TAG", response.body().string());
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static String getAnimeID(int malID) {
        OkHttpClient client = new OkHttpClient();
        String query = "{\"query\":\"query { Media (idMal: " +
                malID +
                ", type: ANIME) { id } }\"}";
        Request request = new Request.Builder()
                .url("https://graphql.anilist.co")
                .post(RequestBody.create(MediaType.parse("application/json"), query))
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            JSONObject data = new JSONObject(responseBody);
            return data
                    .getJSONObject("data")
                    .getJSONObject("Media")
                    .getString("id");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

}
