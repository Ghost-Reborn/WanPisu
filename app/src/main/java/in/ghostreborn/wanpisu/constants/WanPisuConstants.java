package in.ghostreborn.wanpisu.constants;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.model.AllManga;
import in.ghostreborn.wanpisu.model.Servers;
import in.ghostreborn.wanpisu.model.WanPisu;
import in.ghostreborn.wanpisu.model.WanPisuEpisodes;

public class WanPisuConstants {
    public static String MANGA_ID;
    public static String ALL_ANIME_ID;
    public static String ALL_ANIME_EPISODE_NUMBER = "1";
    public static ArrayList<WanPisu> wanPisus;
    public static ArrayList<WanPisuEpisodes> episodes;
    public static ArrayList<String> mangaChapters;
    public static String animeThumbnail;
    public static int ALL_ANIME_EPISODE_ADD = 0;
    public static boolean isHLS;
    public static int EPISODE_VISIBLE = 25;
    public static int PAGE = 0;
    public static ArrayList<AllManga> allMangas;
    public static String MANGA_CHAPTER = "1";


    // New Stuffs
    public static String AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/121.0";
    public static String ALL_ANIME_REFER = "https://allanime.to";
    public static String ALL_ANIME_BASE = "allanime.day";
    public static String ALL_ANIME_API = "https://api." + ALL_ANIME_BASE + "/api";

    public static boolean EPISODE_360P_AVAILABLE = false;
    public static boolean EPISODE_480P_AVAILABLE = false;
    public static boolean EPISODE_720P_AVAILABLE = false;
    public static boolean EPISODE_1080P_AVAILABLE = false;

    public static ArrayList<Servers> servers;
    public static String SERVER_SAK = "Sak";
    public static String SERVER_LUF_MP4 = "Luf-mp4";
    public static String SERVER_DEFAULT = "Default";
    public static String SERVER_S_MP4 = "S-mp4";
    public static String SERVER_UV_MP4 = "Uv-mp4";

}
