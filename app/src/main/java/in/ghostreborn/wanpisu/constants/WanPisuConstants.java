package in.ghostreborn.wanpisu.constants;

import android.content.SharedPreferences;

import java.io.File;
import java.util.ArrayList;

import in.ghostreborn.wanpisu.model.AnimeDown;
import in.ghostreborn.wanpisu.model.Kitsu;
import in.ghostreborn.wanpisu.model.KitsuEpisode;
import in.ghostreborn.wanpisu.model.WanPisu;

public class WanPisuConstants {

    public static final String WAN_PISU_PREFERENCE = "WAN_PISU";

    public static final String ALL_ANIME_ANIME_ID = "ALL_ANIME_ANIME_ID";
    public static final String ALL_ANIME_ANIME_EPISODES = "ALL_ANIME_ANIME_EPISODES";
    public static final String ALL_ANIME_ANIME_EPISODE_NUMBER = "ALL_ANIME_ANIME_EPISODE_NUMBER";
    public static final String ALL_ANIME_ANIME_SERVER = "ALL_ANIME_ANIME_SERVER";
    public static final String ALL_ANIME_ANIME_NAME = "ALL_ANIME_ANIME_NAME";
    public static final String ALL_ANIME_ANIME_THUMBNAIL = "ALL_ANIME_ANIME_THUMBNAIL";

    public static final String KITSU_LOGIN_FINISHED = "KITSU_LOGIN_FINISHED";
    public static final String KITSU_TOKEN = "KITSU_TOKEN";
    public static final String KITSU_USER_ID = "KITSU_USER_ID";
    public static final String KITSU_ANIME_ID = "KITSU_ANIME_ID";
    public static final String KITSU_ANIME_INDEX = "KITSU_ANIME_INDEX";
    public static final String KITSU_ANIME_NAME = "KITSU_ANIME_NAME";

    public static final String KITSU_PROGRESS_CURRENT = "current";
    public static final String KITSU_PROGRESS_COMPLETED = "completed";
    public static final String KITSU_PROGRESS_ON_HOLD = "on_hold";
    public static final String KITSU_PROGRESS_DROPPED = "dropped";
    public static final String KITSU_PROGRESS_PLANNED = "planned";
    public static final String KITSU_PROGRESS_NONE = "none";

    public static boolean hasNext = false;
    public static boolean isUserAnime = false;
    public static String nextURL = "";

    public static ArrayList<WanPisu> animeNames;
    public static ArrayList<Kitsu> kitsus;
    public static ArrayList<Kitsu> userKitsus;
    public static ArrayList<Kitsu> userCurrentKitsus;
    public static ArrayList<KitsuEpisode> kitsuEpisodes;
    public static ArrayList<AnimeDown> animeDowns;
    public static ArrayList<String> animeServes;
    public static ArrayList<String> animeOffsets;

    public static SharedPreferences preferences;

    public static File wanPisuFolder;

}
