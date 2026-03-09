package br.com.projetojoao.projetospring.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSeason(@JsonAlias("Season")Integer seasonNumber,
                         @JsonAlias("Episodes")List<DataEpisode> eps) {


    public void getSeasonInfo(){
        System.out.println("\nSeason: " + this.seasonNumber);
        for(DataEpisode episode : eps){
            episode.getEpisodeInfo();
        }
    }
}
