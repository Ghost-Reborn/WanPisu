package in.ghostreborn.wanpisu.constants;

import android.content.SharedPreferences;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.model.Kitsu;
import in.ghostreborn.wanpisu.model.KitsuDetails;
import in.ghostreborn.wanpisu.model.WanPisu;

public class WanPisuConstants {

    public static final String WAN_PISU_PREFERENCE = "WAN_PISU";

    public static final String ALL_ANIME_ANIME_ID = "ALL_ANIME_ANIME_ID";
    public static final String ALL_ANIME_ANIME_EPISODES = "ALL_ANIME_ANIME_EPISODES";
    public static final String ALL_ANIME_ANIME_EPISODE_NUMBER = "ALL_ANIME_ANIME_EPISODE_NUMBER";

    public static final String KITSU_LOGIN_FINISHED = "KITSU_LOGIN_FINISHED";
    public static final String KITSU_TOKEN = "KITSU_TOKEN";
    public static final String KITSU_USER_ID = "KITSU_USER_ID";
    public static final String KITSU_ANIME_ID = "KITSU_ANIME_ID";
    public static final String KITSU_ANIME_INDEX = "KITSU_ANIME_INDEX";
    public static final String KITSU_ANIME_NAME = "KITSU_ANIME_NAME";

    public static boolean hasNext = false;
    public static String nextURL = "";

    public static ArrayList<WanPisu> animeNames;
    public static ArrayList<Kitsu> kitsus;
    public static ArrayList<Kitsu> userKitsus;
    public static ArrayList<KitsuDetails> kitsuDetails;

    public static SharedPreferences preferences;

}
