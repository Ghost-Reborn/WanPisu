package in.ghostreborn.wanpisu.model;

public class AnimeDown {

    String animeName;
    int progress;

    public AnimeDown(String animeName, int progress){
        this.animeName = animeName;
        this.progress = progress;
    }

    public String getAnimeName() {
        return animeName;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
