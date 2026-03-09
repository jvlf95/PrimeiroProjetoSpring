package br.com.projetojoao.projetospring.model;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataEpisode(@JsonProperty("Title")String title,
                          @JsonProperty("Released")String released,
                          @JsonProperty("Episode")Integer epNumber,
                          @JsonAlias("imdbRating")String rating ){

    public void getEpisodeInfo() {
        System.out.println("\nTitle: " + this.title +
                "\nReleased: " + this.released +
                "\nEpisode: " + this.epNumber +
                "\nRating: " + this.rating);
    }



}
