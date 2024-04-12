package in.ghostreborn.wanpisu.model;

import java.util.ArrayList;

public class WanPisu {

    String id;
    String name;
    String thumbnail;
    String lastEpisode;
    ArrayList<WanPisuEpisodes> availableEpisodes;

    public WanPisu(
            String id,
            String name,
            String thumbnail,
            String lastEpisode,
            ArrayList<WanPisuEpisodes> availableEpisodes
    ){
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.lastEpisode = lastEpisode;
        this.availableEpisodes = availableEpisodes;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastEpisode() {
        return lastEpisode;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public ArrayList<WanPisuEpisodes> getAvailableEpisodes() {
        return availableEpisodes;
    }
}
