package br.com.projetojoao.projetospring.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSerie(@JsonAlias("Title")String title,
                        @JsonAlias("imdbRating") String rating,
                        @JsonAlias("totalSeasons")Integer totalSeasons) {

    public void getSerieInfo(){
        System.out.println("Title: " + this.title +
                "\nTotal Seasons: " + this.totalSeasons +
                "\nRating: " + this.rating);
    }
}
