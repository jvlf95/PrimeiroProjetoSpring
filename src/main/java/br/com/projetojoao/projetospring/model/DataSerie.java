package br.com.projetojoao.projetospring.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSerie(@JsonAlias("Title")String title,
                        @JsonAlias("imdbRating") String rating,
                        @JsonAlias("totalSeasons")Integer totalSeasons,
                        @JsonAlias("Genre") String genre,
                        @JsonAlias("Actors") String actors,
                        @JsonAlias("Plot") String plot,
                        @JsonAlias("Poster") String poster) {
}
