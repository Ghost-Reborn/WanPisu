package in.ghostreborn.wanpisu.model;

public class AnimeDown {

    String animeName;
    int episode;
    int progress;
    String server;
    boolean isDownloadFinished;

    public AnimeDown(
            String animeName,
            int episode,
            int progress,
            String server,
            boolean isDownloadFinished
    ) {
        this.animeName = animeName;
        this.episode = episode;
        this.progress = progress;
        this.server = server;
        this.isDownloadFinished = isDownloadFinished;
    }

    public String getAnimeName() {
        return animeName;
    }

    public int getEpisode() {
        return episode;
    }

    public String getServer() {
        return server;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public boolean isDownloadFinished() {
        return isDownloadFinished;
    }
}
