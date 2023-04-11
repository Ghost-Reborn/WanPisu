package in.ghostreborn.wanpisu.model;

public class WanPisu {

    private final String animeID;
    private final String animeName;
    private final String animeThumbnailUrl;
    private final int totalEpisodes;
    private final String malID;

    public WanPisu(String animeID, String animeName, String animeThumbnailUrl, int totalEpisodes, String malID) {
        this.animeID = animeID;
        this.animeName = animeName;
        this.animeThumbnailUrl = animeThumbnailUrl;
        this.totalEpisodes = totalEpisodes;
        this.malID = malID;
    }

    public String getAnimeID() {
        return animeID;
    }

    public String getAnimeName() {
        return animeName;
    }

    public String getAnimeThumbnailUrl() {
        return animeThumbnailUrl;
    }

    public int getTotalEpisodes() {
        return totalEpisodes;
    }

    public String getMalID() {
        return malID;
    }
}
