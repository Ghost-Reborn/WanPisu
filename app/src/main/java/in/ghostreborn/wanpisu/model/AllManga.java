package in.ghostreborn.wanpisu.model;

import java.util.ArrayList;

public class AllManga {

    String name;
    String id;
    String thumbnail;
    ArrayList<String> availableChapters;

    public AllManga(
            String name,
            String id,
            String thumbnail,
            ArrayList<String> availableChapters
    ){
        this.name = name;
        this.id = id;
        this.thumbnail = thumbnail;
        this.availableChapters = availableChapters;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public ArrayList<String> getAvailableChapters() {
        return availableChapters;
    }
}
