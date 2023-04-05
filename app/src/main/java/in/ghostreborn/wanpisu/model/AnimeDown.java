package in.ghostreborn.wanpisu.model;

public class AnimeDown {

    String animeName;
    String episode;
    int progress;

    public AnimeDown(String animeName, String episode,int progress){
        this.animeName = animeName;
        this.episode = episode;
        this.progress = progress;
    }

    public String getAnimeName() {
        return animeName;
    }

    public String getEpisode() {
        return episode;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
