package in.ghostreborn.wanpisu.parser;

public class Anilist {

    public static final String CLIENT_ID = "11820";
    public static final String ANILIST_BASE_URL = "https://anilist.co/";
    public static final String ANILIST_TOKEN_URL = ANILIST_BASE_URL +
            "api/v2/oauth/authorize?client_id=" +
            CLIENT_ID +
            "&response_type=token";

}
