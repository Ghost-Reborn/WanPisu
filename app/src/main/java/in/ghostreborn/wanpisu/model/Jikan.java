package in.ghostreborn.wanpisu.model;

public class Jikan {

    private String animeTitle;
    private String animeThumbnail;
    private String animeSynopsis;

    public Jikan(String animeTitle, String animeThumbnail, String animeSynopsis){
        this.animeTitle = animeTitle;
        this.animeThumbnail = animeThumbnail;
        this.animeSynopsis = animeSynopsis;
    }

    public String getAnimeThumbnail() {
        return animeThumbnail;
    }

    public String getAnimeTitle() {
        return animeTitle;
    }

    public String getAnimeSynopsis() {
        return animeSynopsis;
    }
}
