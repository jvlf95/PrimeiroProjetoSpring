package br.com.projetojoao.projetospring.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSerie(@JsonAlias("Title")String title,
                        @JsonAlias("imdbRating") Double rating,
                        @JsonAlias("totalSeasons")String totalSeasons) {

    public void getDataSerie(){
        System.out.println("Title: " + this.title +
                "\nTotal Seasons: " + this.totalSeasons +
                "\nRating: " + this.rating);
    }
}
