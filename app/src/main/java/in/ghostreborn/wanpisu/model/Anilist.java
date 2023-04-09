package in.ghostreborn.wanpisu.model;

public class Anilist {

    String animeName;
    String animeImageUrl;
    String malID;

    public Anilist(String animeName, String animeImageUrl, String malID){
        this.animeName = animeName;
        this.animeImageUrl = animeImageUrl;
        this.malID = malID;
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
}
