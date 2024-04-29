package in.ghostreborn.wanpisu.constants;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.model.AllManga;
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

    public static String AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/121.0";
    public static String ALL_ANIME_REFER = "https://allanime.to";
    public static String ALL_ANIME_BASE = "allanime.day";
    public static String ALL_ANIME_API = "https://api." + ALL_ANIME_BASE + "/api";

}
