package in.ghostreborn.wanpisu.model;

public class KitsuDetails {

    String anime, thumbnail;

    public KitsuDetails(String anime, String thumbnail){
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
