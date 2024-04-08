package in.ghostreborn.wanpisu.model;

import java.util.ArrayList;

public class Anilist {

    String animeName;
    String animeImageUrl;
    String malID;
    String anilistID;
    String allAnimeID;
    String episodesString;

    public Anilist(
            String animeName,
            String animeImageUrl,
            String malID,
            String anilistID,
            String allAnimeID,
            String episodesString
    ){
        this.animeName = animeName;
        this.animeImageUrl = animeImageUrl;
        this.malID = malID;
        this.anilistID = anilistID;
        this.allAnimeID = allAnimeID;
        this.episodesString = episodesString;
    }

    public String getAnimeImageUrl() {
        return animeImageUrl;
    }

    public String getAnimeName() {
        return animeName;
    }

    public String getMalID() {
        return malID;
    }

    public String getAnilistID() {
        return anilistID;
    }

    public String getAllAnimeID() {
        return allAnimeID;
    }

    public String getEpisodesString() {
        return episodesString;
    }
}
