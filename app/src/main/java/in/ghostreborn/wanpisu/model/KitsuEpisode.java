package in.ghostreborn.wanpisu.model;

public class KitsuEpisode {

    String episodeNumber, title, thumbnail;

    public KitsuEpisode(String episodeNumber, String title, String thumbnail){
        this.episodeNumber = episodeNumber;
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getEpisodeNumber() {
        return episodeNumber;
    }

    public String getTitle() {
        return title;
    }
}
