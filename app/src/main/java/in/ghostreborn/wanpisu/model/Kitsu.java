package in.ghostreborn.wanpisu.model;

public class Kitsu {

    String anime, thumbnail;

    public Kitsu(String anime, String thumbnail){
        this.anime = anime;
        this.thumbnail = thumbnail;
    }

    public String getAnime() {
        return anime;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
