package in.ghostreborn.wanpisu.model;

public class Servers {

    String serverName;
    String serverUrl;
    boolean isHls;

    public Servers(String serverName, String serverUrl, boolean isHls) {
        this.serverName = serverName;
        this.serverUrl = serverUrl;
        this.isHls = isHls;
    }

    public String getServerName() {
        return serverName;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public boolean getIsHls() {
        return isHls;
    }
}
