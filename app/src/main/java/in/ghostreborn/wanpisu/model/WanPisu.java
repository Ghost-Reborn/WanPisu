package in.ghostreborn.wanpisu.model;

import java.util.ArrayList;

public class WanPisu {

    private final String animeID;
    private final String animeName;
    private final String animeThumbnailUrl;
    private final String malID;
    private final ArrayList<String> availableEpisodes;

    public WanPisu(
            String animeID,
            String animeName,
            String animeThumbnailUrl,
            String malID,
            ArrayList<String> availableEpisodes
    ) {
        this.animeID = animeID;
        this.animeName = animeName;
        this.animeThumbnailUrl = animeThumbnailUrl;
        this.malID = malID;
        this.availableEpisodes = availableEpisodes;
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

    public String getMalID() {
        return malID;
    }

    public ArrayList<String> getAvailableEpisodes() {
        return availableEpisodes;
    }
}
