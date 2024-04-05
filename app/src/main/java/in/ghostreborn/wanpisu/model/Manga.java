package in.ghostreborn.wanpisu.model;

public class Manga {

    String page;
    String url;

    public Manga(
            String page,
            String url
    ){
        this.page = page;
        this.url = url;
    }

    public String getPage() {
        return page;
    }

    public String getUrl() {
        return url;
    }
}
