package in.ghostreborn.wanpisu.model;

public class JikanEpisodes {

    String episodeNumber;
    String episodeTitle;

    public JikanEpisodes(String episodeTitle, String episodeNumber){
        this.episodeTitle = episodeTitle;
        this.episodeNumber = episodeNumber;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public String getEpisodeNumber() {
        return episodeNumber;
    }
}
