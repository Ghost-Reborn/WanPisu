package in.ghostreborn.wanpisu.model;

public class Servers {

    String serverName;
    String serverUrl;

    public Servers(String serverName, String serverUrl) {
        this.serverName = serverName;
        this.serverUrl = serverUrl;
    }

    public String getServerName() {
        return serverName;
    }

    public String getServerUrl() {
        return serverUrl;
    }
}
