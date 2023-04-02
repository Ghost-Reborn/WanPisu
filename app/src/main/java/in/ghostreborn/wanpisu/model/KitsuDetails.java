package in.ghostreborn.wanpisu.model;

public class KitsuDetails {

    String anime, thumbnail, description, averageRating, episodes;

    public KitsuDetails(
            String anime,
            String thumbnail,
            String description,
            String averageRating,
            String episodes
    ){
        this.anime = anime;
        this.thumbnail = thumbnail;
        this.description = description;
        this.averageRating = averageRating;
        this.episodes = episodes;
    }

    public String getAnime() {
        return anime;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public String getDescription() {
        return description;
    }

    public String getEpisodes() {
        return episodes;
    }
}
