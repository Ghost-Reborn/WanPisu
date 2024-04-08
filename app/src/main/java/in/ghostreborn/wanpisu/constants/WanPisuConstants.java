package in.ghostreborn.wanpisu.constants;

import android.content.SharedPreferences;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.model.AllManga;
import in.ghostreborn.wanpisu.model.Anilist;
import in.ghostreborn.wanpisu.model.Jikan;
import in.ghostreborn.wanpisu.model.JikanEpisodes;
import in.ghostreborn.wanpisu.model.WanPisu;

public class WanPisuConstants {

    public static final String WAN_PISU_PREFERENCE = "WAN_PISU";
    public static final String WAN_PISU_PREFERENCE_ENABLE_DUB = "key_enable_sub_dub";
    public static final String WAN_PISU_PREFERENCE_ENABLE_UNKNOWN = "key_enable_unknown";
    public static final String WAN_PISU_ANILIST_TOKEN = "WAN_PISU_ANILIST_TOKEN";
    public static final String ANILIST_USER_NAME = "ANILIST_USER_NAME";
    public static final String ANILIST_SAVED_LOCALLY = "ANILIST_SAVED_LOCALLY";
    public static int ALL_ANIME_EPISODE_ADD = 0;

    public static SharedPreferences preferences;

    public static ArrayList<Anilist> anilists;
    public static ArrayList<WanPisu> wanPisus;
    public static ArrayList<AllManga> allMangas;

    public static WanPisu wanPisu;

    public static ArrayList<Jikan> jikans;
    public static ArrayList<JikanEpisodes> jikanEpisodes;
    public static ArrayList<String> animeEpisodes;
    public static int ANIME_INDEX = 0;
    public static int ANIME_MAL_ID = 0;

    public static boolean isHLS = false;

    public static String animeImageURL = "";

    public static boolean isDubEnabled= false;

    // Anime status constants
    public static final String ANIME_CURRENT = "CURRENT";
    public static final String ANIME_PLANNING = "PLANNING";
    public static final String ANIME_COMPLETED = "COMPLETED";
    public static final String ANIME_DROPPED = "DROPPED";
    public static final String ANIME_PAUSED = "PAUSED";
    public static final String ANIME_REPEATING = "REPEATING";

    // Database
    public static final String TABLE_NAME = "anilist";
    public static final String COLUMN_ANIME_ANILIST_ID = "anime_anilist_id";
    public static final String COLUMN_ALL_ANIME_ID = "all_anime_id";
    public static final String COLUMN_ANIME_MAL_ID = "anime_mal_id";
    public static final String COLUMN_ANIME_NAME = "anime_name";
    public static final String COLUMN_ANIME_IMAGE_URL = "anime_image_url";
    public static final String COLUMN_ANIME_AVAILABLE_EPISODES = "anime_available_episodes";

    public static String ALL_ANIME_ID = "";
    public static String ALL_ANIME_EPISODE_NUMBER = "";
    public static String MANGA_ID = "";
}
