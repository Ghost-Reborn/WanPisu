package in.ghostreborn.wanpisu.model;

public class WanPisuEpisodes {

    String episodeNumber;
    String episodeTitle;

    public WanPisuEpisodes(String episodeNumber, String episodeTitle){
        this.episodeNumber = episodeNumber;
        this.episodeTitle = episodeTitle;
    }

    public String getEpisodeNumber() {
        return episodeNumber;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public void setEpisodeTitle(String episodeTitle) {
        this.episodeTitle = episodeTitle;
    }
}
