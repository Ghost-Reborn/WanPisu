package in.ghostreborn.wanpisu.model;

public class WanPisuEpisodes {

    String episodeNumber;
    String episodeTitle;
    String episodeThumbnail;

    public WanPisuEpisodes(String episodeNumber, String episodeTitle, String episodeThumbnail){
        this.episodeNumber = episodeNumber;
        this.episodeTitle = episodeTitle;
        this.episodeThumbnail = episodeThumbnail;
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

    public String getEpisodeThumbnail() {
        return episodeThumbnail;
    }

    public void setEpisodeThumbnail(String episodeThumbnail) {
        this.episodeThumbnail = episodeThumbnail;
    }
}
