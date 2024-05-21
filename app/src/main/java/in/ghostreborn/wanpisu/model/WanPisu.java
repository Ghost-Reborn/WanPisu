package in.ghostreborn.wanpisu.model;

public class WanPisu {

    String id;
    String name;
    String thumbnail;

    public WanPisu(
            String id,
            String name,
            String thumbnail
    ){
        this.id = id;
        this.name = name;
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
