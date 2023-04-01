package in.ghostreborn.wanpisu.model;

public class Kitsu {

    String animeID,anime, thumbnail;

    public Kitsu(String animeID,String anime, String thumbnail){
        this.anime = anime;
        this.thumbnail = thumbnail;
        this.animeID = animeID;
    }

    public String getAnime() {
        return anime;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getAnimeID() {
        return animeID;
    }
}
