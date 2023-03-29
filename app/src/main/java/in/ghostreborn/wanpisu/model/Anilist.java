package in.ghostreborn.wanpisu.model;

public class Anilist {

    String animeName;
    String animeImageUrl;

    public Anilist(String animeName, String animeImageUrl){
        this.animeName = animeName;
        this.animeImageUrl = animeImageUrl;
    }

    public String getAnimeImageUrl() {
        return animeImageUrl;
    }

    public String getAnimeName() {
        return animeName;
    }
}
