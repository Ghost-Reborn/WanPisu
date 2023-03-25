package in.ghostreborn.wanpisu;

public enum URLType {
    MP4(""),
    HLS("");

    private String url;

    URLType(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
