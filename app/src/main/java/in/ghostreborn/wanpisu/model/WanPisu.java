package in.ghostreborn.wanpisu.model;

public class WanPisu {

    private final String animeID;
    private final String animeName;
    private final String animeThumbnailUrl;
    private final int totalEpisodes;

    public WanPisu(String animeID, String animeName, String animeThumbnailUrl, int totalEpisodes) {
        this.animeID = animeID;
        this.animeName = animeName;
        this.animeThumbnailUrl = animeThumbnailUrl;
        this.totalEpisodes = totalEpisodes;
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
}
