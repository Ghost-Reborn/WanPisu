package in.ghostreborn.wanpisu.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.parser.AnilistParser;

public class AnilistUtils {

    public static void checkAnilist(Context context){
        SharedPreferences preferences = context.getSharedPreferences(WanPisuConstants.WAN_PISU_PREFERENCE, Context.MODE_PRIVATE);
        if (!preferences.contains(WanPisuConstants.WAN_PISU_ANILIST_TOKEN)){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(AnilistParser.ANILIST_TOKEN_URL));
            context.startActivity(intent);
        }else {
            String TOKEN = preferences.getString(WanPisuConstants.WAN_PISU_ANILIST_TOKEN, "");
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

}
