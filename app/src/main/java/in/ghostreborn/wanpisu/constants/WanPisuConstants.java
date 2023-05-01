package in.ghostreborn.wanpisu.constants;

import android.content.SharedPreferences;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.model.Anilist;
import in.ghostreborn.wanpisu.model.Jikan;
import in.ghostreborn.wanpisu.model.WanPisu;

public class WanPisuConstants {

    public static final String WAN_PISU_PREFERENCE = "WAN_PISU";
    public static final String WAN_PISU_PREFERENCE_ENABLE_DUB = "key_enable_sub_dub";
    public static final String WAN_PISU_PREFERENCE_ENABLE_UNKNOWN = "key_enable_unknown";
    public static final String WAN_PISU_ANILIST_TOKEN = "WAN_PISU_ANILIST_TOKEN";
    public static final String ANILIST_USER_NAME = "ANILIST_USER_NAME";

    public static SharedPreferences preferences;

    public static ArrayList<Anilist> anilists;
    public static ArrayList<WanPisu> wanPisus;
    public static ArrayList<Jikan> jikans;
    public static int ANIME_INDEX = 0;
    public static int ANIME_MAL_ID = 0;

    public static boolean isDubEnabled= false;
    public static boolean isLogged = false;

    // Anime status constants
    public static final String ANIME_CURRENT = "CURRENT";
    public static final String ANIME_PLANNING = "PLANNING";
    public static final String ANIME_COMPLETED = "COMPLETED";
    public static final String ANIME_DROPPED = "DROPPED";
    public static final String ANIME_PAUSED = "PAUSED";
    public static final String ANIME_REPEATING = "REPEATING";

}
