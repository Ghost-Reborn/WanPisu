package in.ghostreborn.wanpisu.model;

public class WanPisu {

    private final String animeID;
    private final String animeName;
    private final String animeThumbnailUrl;

    public WanPisu(String animeID, String animeName, String animeThumbnailUrl) {
        this.animeID = animeID;
        this.animeName = animeName;
        this.animeThumbnailUrl = animeThumbnailUrl;
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
}
