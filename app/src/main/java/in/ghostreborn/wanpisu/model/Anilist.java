package in.ghostreborn.wanpisu.model;

public class Anilist {

    String animeName;
    String animeImageUrl;
    String malID;
    String anilistID;
    String allAnimeID;

    public Anilist(
            String animeName,
            String animeImageUrl,
            String malID,
            String anilistID,
            String allAnimeID
    ){
        this.animeName = animeName;
        this.animeImageUrl = animeImageUrl;
        this.malID = malID;
        this.anilistID = anilistID;
        this.allAnimeID = allAnimeID;
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
}
