package in.ghostreborn.wanpisu.model;

public class AllManga {

    String name;
    String id;
    String thumbnail;

    public AllManga(
            String name,
            String id,
            String thumbnail
    ){
        this.name = name;
        this.id = id;
        this.thumbnail = thumbnail;
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
}
