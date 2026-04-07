package br.com.projetojoao.projetospring.model;

import jakarta.persistence.*;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;

import java.util.OptionalDouble;

@Entity
@Table(name = "serie")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto incremental
    private Long id;
    @Column(unique = true)
    private String title;
    private Double rating;
    private Integer totalSeasons;
    @Enumerated(EnumType.STRING)
    private Category genre;
    private String plot;
    private String actors;

    public Serie(DataSerie dataSerie){
        this.title = dataSerie.title();
        // do the same thing of try / catch
        this.rating = OptionalDouble.of(Double.valueOf(dataSerie.rating())).orElse(0.0);
        this.totalSeasons = dataSerie.totalSeasons();
        // method split() -> we pass a separator and select the index of the String we need
        this.genre = Category.fromString(dataSerie.genre().split(",")[0].trim());
        this.plot = dataSerie.plot();
        this.actors = dataSerie.actors();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(int totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public Category getGenre() {
        return genre;
    }

    public void setGenre(Category genre) {
        this.genre = genre;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    @Override
    public String toString(){
        return "\nTitle: " + getTitle() +
                "\nTotal Seasons: " + getTotalSeasons() +
                "\nRating: " + getRating() +
                "\nGenre: " + getGenre() +
                "\nActors: " + getActors() +
                "\nPlot: " + getPlot();
    }
}
