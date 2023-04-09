package in.ghostreborn.wanpisu.model;

public class Anilist {

    String animeName;
    String animeImageUrl;
    String malID;
    String anilistID;

    public Anilist(String animeName, String animeImageUrl, String malID, String anilistID){
        this.animeName = animeName;
        this.animeImageUrl = animeImageUrl;
        this.malID = malID;
        this.anilistID = anilistID;
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
}
