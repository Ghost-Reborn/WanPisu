package in.ghostreborn.wanpisu.model;

public class Kitsu {

    String animeID,anime, description,thumbnail, status, progress, totalEpisodes, rating;

    public Kitsu(
            String animeID,
            String anime,
            String description,
            String thumbnail,
            String status,
            String progress,
            String totalEpisodes,
            String rating
    ){
        this.anime = anime;
        this.thumbnail = thumbnail;
        this.description = description;
        this.animeID = animeID;
        this.status = status;
        this.progress = progress;
        this.totalEpisodes = totalEpisodes;
        this.rating = rating;
    }

    public String getAnime() {
        return anime;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getAnimeID() {
        return animeID;
    }

    public String getDescription() {
        return description;
    }

    public String getProgress() {
        return progress;
    }

    public String getStatus() {
        return status;
    }

    public String getTotalEpisodes() {
        return totalEpisodes;
    }

    public String getRating() {
        return rating;
    }
}
