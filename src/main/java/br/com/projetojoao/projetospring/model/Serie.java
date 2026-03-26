package br.com.projetojoao.projetospring.model;

import java.util.OptionalDouble;

public class Serie {
    private String title;
    private double rating;
    private int totalSeasons;
    private Category genre;
    private String plot;
    private String actors;

    public Serie(DataSerie dataSerie){
        this.title = dataSerie.title();
        // do the same thing of try / catch
        this.rating = OptionalDouble.of(Double.valueOf(dataSerie.rating())).orElse(0);
        this.totalSeasons = dataSerie.totalSeasons();
        // method split() -> we pass a separator e select the index of the String we need
        this.genre = Category.fromString(dataSerie.genre().split(",")[0].trim());
        this.plot = dataSerie.plot();
        this.actors = dataSerie.actors();
    }



}
